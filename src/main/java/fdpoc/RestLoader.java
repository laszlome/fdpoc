package fdpoc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Base class to load list of entities from the API
 * 
 * @author Laszlo.Merenyi
 * 
 * Pages per call (max 100): "per_page=X" Paging: "page=X"
 *
 */
public class RestLoader<T> {
	
	/**
	 * @author Laszlo.Merenyi
	 *
	 */
	public enum UPDATE_MODE {
	    UPDATE_ADD_ITEMS,
	    UPDATE_REMOVE_ITEMS
	}
	
	int counter = 0;
	
	final int START_PAGE = 1; // first page = 1
	final int ENTITIES_PER_PAGE = 100; // 100: the most efficient way to load everything (max: 100 using API v2)

	final boolean DEVELOPER_OPTION_LOAD_1ST_PAGE_ONLY = false; // for testing (to shorten startup time)

	private String uri;
	
	MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
	
	long id_counter = 1L;
	
	final String API_KEY = "Basic SECRET_PRIVATE_DATA_SORRY";

	// Ignite cache of the appropriate class - key is always Long
	protected IgniteCache<Long, T> cache;

	protected ClientConfig clientConfig = new DefaultClientConfig();

	Class<T> returnType;
	
	/**
	 * @param uri
	 * @param returnType
	 * @return
	 */
	public static final <T> RestLoader<?> getLoader(String uri, Class<T> returnType, IgniteCache<Long, T> cache) {
		return new RestLoader<T>(uri, returnType, cache);
	}
	
	/**
	 * Constructor
	 * 
	 * @param cache
	 * @param uri
	 */
	public RestLoader(String uri, Class<T> returnType, IgniteCache<Long, T> cache) {
		this.uri = uri;
		this.returnType = returnType;
		this.cache = cache;
		init();
	}
	
	
	/**
	 * initialize the class
	 */
	private void init(){
		clientConfig.getClasses().add(JacksonJsonProvider.class);
	}

	
	/**
	 * @param param
	 * @param value
	 */
	protected void addOrReplaceParameter(String param, String value) {
		queryParams.remove(param);
		queryParams.add(param, value);
	}
	
	/**
	 * Loading entities using the FreshDesk APi
	 */
	public void updateEntities(UPDATE_MODE updateMode) {

		id_counter = 1L;
		
		try {

			// Using Jersey to load stuff via the FreshDesk API
			Client client = Client.create(clientConfig);

			queryParams.add("per_page", "" + ENTITIES_PER_PAGE); // entities per page (max 100)

			boolean bLoadMore = true;
			int page = START_PAGE;

			// Try to load as many pages as needed
			while (bLoadMore) {

				// Use this for testing
				if (DEVELOPER_OPTION_LOAD_1ST_PAGE_ONLY) {
					bLoadMore = false; // stop after the 1st iteration (for testing only)
				}

				System.out.printf("Loading page %s (max entities: %s) \n", page, ENTITIES_PER_PAGE);

				// set parameters for request:
				queryParams.remove("page");
				queryParams.add("page", "" + page++); // starting page

				WebResource webResource = client.resource(uri);
				ClientResponse response = webResource.queryParams(queryParams).header("Authorization", API_KEY)
						.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

				if (response.getStatus() != 200) {
					System.err.println("Failed : HTTP error code : " + response.getStatus());
					bLoadMore = false;

					switch (response.getStatus()) {
					case 429:
						System.err.println("Rate limit has been exhausted.");
						break;

					default:
						break;
					}
				} else
				{
					System.out.printf(" Got response from server, processing...");
				}

				// debug if needed - but then comment out the next part as no further processing will succeed...
//				String output = response.getEntity(String.class);
//				System.out.println("Response - length: " + output.length() + " bytes");
//				System.out.println(output); // can be checked as a String

				GenericType<Collection<T>> type = getParameterizedCollectionType();
				Collection<T> entities = response.getEntity(type);

				if (entities != null) {
					System.out.printf(" Entities loaded: %s\n", entities.size());
					
					Map<Long, T> one_page_contents = Maps.uniqueIndex(entities, entity -> {

						Long id = 0L;
						if (entity instanceof CommonCacheEntry) {
							id = ((CommonCacheEntry) entity).getId();
						} else {
							id = id_counter++;
						}

						return id;
					});
					
					
					if (one_page_contents.size() > 0)
					{
						for (T t: one_page_contents.values()){
							System.out.println(" Class is: " + t.getClass().getName());
							break;
						}
					}

					if (cache != null) {
						
						if (updateMode == UPDATE_MODE.UPDATE_ADD_ITEMS) {
							// Adding items... 
							cache.putAll(one_page_contents);

							if (!one_page_contents.keySet().isEmpty()) {
								System.out.printf(" %s entities ADDED to the cache:", one_page_contents.size());
								debugKeys("Added keys: ", one_page_contents.keySet());
							}

						} else if (updateMode == UPDATE_MODE.UPDATE_REMOVE_ITEMS) {
							// Removing items... 
							cache.removeAll(one_page_contents.keySet());

							if (!one_page_contents.keySet().isEmpty()) {
								System.out.printf(" %s entities have been REMOVED from the cache:", one_page_contents.size());
								debugKeys("Removed keys: ", one_page_contents.keySet());
							}
						}
							
					} else
					{
						System.out.println("[WARNING] Running in test or debug mode - no cache is populated.");
					}
					
				}

				System.out.println(" Rate limit - remaining call count for the hour: "
						+ response.getHeaders().get("X-RateLimit-Remaining") + " of "
						+ response.getHeaders().get("X-RateLimit-Total"));

				// Determine if there are more pages to load:
				List<String> link2NextPage = response.getHeaders().get("Link");
				if (link2NextPage != null && link2NextPage.size() > 0) {
					System.out.println(" More pages exist! " + link2NextPage.get(0));
				} else {
					System.out.println(" No page at all or last page reached. Cache contains " + (cache==null?"0":cache.size(CachePeekMode.ALL)) + " entries.");
					bLoadMore = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	};
	

	
	/**
	 * @param keySet
	 */
	private void debugKeys(String sAction, Set<Long> keySet) {
		StringBuffer out = new StringBuffer("  " + sAction);
		for (Long id: keySet){
			out.append(id + ", ");
		}
		System.out.println(out.toString().substring(0, out.length()-2));
	}

	/**
	 * We need this to get the right class de-serialized by Jersey
	 * 
	 * @return
	 */
	public GenericType<Collection<T>> getParameterizedCollectionType() {
		ParameterizedType parameterizedGenericType = new ParameterizedType() {

			public Type[] getActualTypeArguments() {
				return new Type[] { returnType };
			}

			public Type getRawType() {
				return Collection.class;
			}

			public Type getOwnerType() {
				return Collection.class;
			}
		};
		
		return new GenericType<Collection<T>>(parameterizedGenericType) {
		};
	}
	
	
}
