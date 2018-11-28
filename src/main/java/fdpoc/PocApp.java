package fdpoc;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fdpoc.model.*;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import fdpoc.RestLoader.UPDATE_MODE;

/**
 * To do's
 * - ticket: load stats fields too!
 * 
 * 
 * @author Laszlo.Merenyi
 *
 */
public class PocApp {

	/* Timer interval for updates */
	final static int UPDATE_FIRSTDELAY = 5 ; // 5 seconds
	final static int UPDATE_INTERVAL =  300; // 5 minutes
	final static int UPDATE_OVERLAP = 300; // 5 minutes
	
	/* A date before we started to use FreshDesk */
	static final DateTime BEGINNING_OF_TIME = new DateTime("2015-01-01T00:00:00Z");
	
	/** Cache names. */
	private static final String CACHE_PRIORITY = "cache_priority";
	private static final String CACHE_STATUS = "cache_status";

	private static final String CACHE_TICKET = "cache_ticket";
	private static final String CACHE_AGENT = "cache_agent";
	private static final String CACHE_PRODUCT = "cache_product";
	private static final String CACHE_COMPANY = "cache_company";
	
	private static final String CACHE_GROUP = "cache_group";
	private static final String CACHE_CONTACT = "cache_contact";
	private static final String CACHE_CONVERSATION = "cache_conversation";
	private static final String CACHE_TIMEENTRIES = "cache_timeentries"; // could be fetched per agent or per ticket
		
	// API URIs
	static final String URI_PRODUCT_LIST = "https://nordpool.freshdesk.com/api/v2/products";
	static final String URI_TICKET_LIST = "https://nordpool.freshdesk.com/api/v2/tickets";
	static final String URI_AGENT_LIST = "https://nordpool.freshdesk.com/api/v2/agents";
	static final String URI_COMPANY_LIST = "https://nordpool.freshdesk.com/api/v2/companies";
	
	static final Ignite ignite = Ignition.start("examples/config/example-ignite.xml"); // original
	// static final Ignite ignite = Ignition.start("config/fdpoc-config.xml");
	// static final IgniteCache<Long, Ticket> cache_ticket = createCache(ignite, CACHE_TICKET, Ticket.class);

	static IgniteCache<Long, Ticket> cache_ticket = null;
	
	static private DateTime lastUpdated = null;

	
	/**
	 * Main - creates caches and load data.
	 * Then run Zeppelin server with ignite 1.8 interpreter to access data.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// ML: it is needed if persistence is on (which is needed to have authentication on)
		// ignite.cluster(). active(true);
		ignite.active(true);

		cache_ticket = createCache(ignite, CACHE_TICKET, Ticket.class);

		System.out.println();
		System.out.println(">>> FDPOC started.");
		
		lastUpdated = new LocalTime().toDateTimeToday();

		// Priority:
		IgniteCache<Long, Priority> cache_priority = createCache(ignite, CACHE_PRIORITY, Priority.class);
		cache_priority.put(1L, new Priority(1L, "Low"));
		cache_priority.put(2L, new Priority(2L, "Medium"));
		cache_priority.put(3L, new Priority(3L, "High"));
		cache_priority.put(4L, new Priority(4L, "Urgent"));

		// Statuses:
		IgniteCache<Long, Status> cache_status = createCache(ignite, CACHE_STATUS, Status.class);
		cache_status.put(1L, new Status(1L, "Open"));
		cache_status.put(2L, new Status(2L, "Pending"));
		cache_status.put(3L, new Status(3L, "Resolved"));
		cache_status.put(4L, new Status(4L, "Closed"));
		cache_status.put(5L, new Status(5L, "Waiting on Customer"));
		cache_status.put(6L, new Status(6L, "Waiting on 3rd party"));
		
		loadAll();
		
		lastUpdated = new LocalTime().toDateTimeToday();
		//updateTickets(new DateTime("2015-01-01T00:00:00Z")); // load everything (a timestamp in the past)
		
		RestLoader<?> loader = getTicketLoader(BEGINNING_OF_TIME); // no filtering by date
		
		performUpdate(loader);
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(() -> runUpdate(), UPDATE_FIRSTDELAY, UPDATE_INTERVAL, TimeUnit.SECONDS);
	}


	/**
	 * Update tickets
	 */
	protected static void runUpdate() {
		LocalTime time = new LocalTime();
		System.out.println("\nRunning full update at " + time.toString() + " - last updated: " + lastUpdated);
		DateTime updateStarted = time.toDateTimeToday();
		
		DateTime updateDataFrom = lastUpdated.minus(UPDATE_OVERLAP);
		
		DateTime updateFrom = new DateTime(updateDataFrom);
		
		System.out.println("--> Updating tickets updated since " + updateFrom.toString());
		
		RestLoader<?> loader = getTicketLoader(updateFrom); // no filtering by date
		
		performUpdate(loader); // load and apply changes
		performRemoveDeleted(loader); // remove deleted tickets
		performRemoveSpam(loader); // remove spam tickets
		
		lastUpdated = updateStarted; // remember this to start update from that time
	}

	/**
	 * Load all auxiliary entities considered to be near "static" (except tickets)
	 */
	protected static void loadAll(){
		// Load data from FreshDesk: 
		System.out.println("--> Loading auxiliary entities...");
		RestLoader.getLoader(URI_PRODUCT_LIST, Product.class, createCache(ignite, CACHE_PRODUCT, Product.class)).updateEntities(UPDATE_MODE.UPDATE_ADD_ITEMS);
		RestLoader.getLoader(URI_COMPANY_LIST, Company.class, createCache(ignite, CACHE_COMPANY, Company.class)).updateEntities(UPDATE_MODE.UPDATE_ADD_ITEMS);
		RestLoader.getLoader(URI_AGENT_LIST, Agent.class, createCache(ignite, CACHE_AGENT, Agent.class)).updateEntities(UPDATE_MODE.UPDATE_ADD_ITEMS);
	}

	
	/**
	 * Returns a partly configured ticket loader 
	 * 
	 * @param updateFrom
	 * @return
	 */
	static protected RestLoader<?> getTicketLoader(DateTime updateFrom){
		RestLoader<?> loader = RestLoader.getLoader(URI_TICKET_LIST, Ticket.class, cache_ticket);
		loader.addOrReplaceParameter("updated_since", updateFrom.toString());
		return loader;
	}
	
	
	/**
	 * update the ticket "table" - can add or delete tickets
	 * 
	 * @param loader the rest loader
	 */
	protected static void performUpdate(RestLoader<?> loader){
		loader.addOrReplaceParameter("include", "stats"); // include closed_at, resolved_at and first_responded_at values
		loader.updateEntities(UPDATE_MODE.UPDATE_ADD_ITEMS); // add them
	}
	
	
	/**
	 * Remove deleted tickets
	 * 
	 * @param loader
	 */
	protected static void performRemoveDeleted(RestLoader<?> loader){
		System.out.println("--> Removing deleted tickets");
		loader.addOrReplaceParameter("filter", "deleted"); // get only the deleted tickets
		loader.updateEntities(UPDATE_MODE.UPDATE_REMOVE_ITEMS); // remove them
	}
	
	
	/**
	 * Remove spam tickets
	 * 
	 * @param loader
	 */
	protected static void performRemoveSpam(RestLoader<?> loader){
		System.out.println("--> Removing spams");
		loader.addOrReplaceParameter("filter", "spam"); // get only spams
		loader.updateEntities(UPDATE_MODE.UPDATE_REMOVE_ITEMS); // remove them
	}	
	
	/**
	 * 
	 * Creates an ignite cache of (Long, Type) 
	 * 
	 * @param ignite
	 * @param cacheName
	 * @param clazz
	 * @return
	 */
	private static final <T> IgniteCache<Long, T> createCache(Ignite ignite, String cacheName, Class<T> clazz) {
		CacheConfiguration<Long, T> cfg = new CacheConfiguration<>(cacheName);
		cfg.setIndexedTypes(Long.class, clazz);
		cfg.setIndexedTypes(String.class, clazz);
		cfg.setIndexedTypes(Date.class, clazz);
		cfg.setIndexedTypes(Boolean.class, clazz);
		IgniteCache<Long, T> cache = ignite.getOrCreateCache(cfg);
		return cache;
	}

}
