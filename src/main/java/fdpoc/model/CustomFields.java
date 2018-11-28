
package fdpoc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * @author Laszlo.Merenyi
 *
 */
@JsonPropertyOrder({ "saga_company_key", "saga_company_name" })
public class CustomFields implements Serializable {

	private static final long serialVersionUID = 1112L;

	@JsonProperty("saga_company_key")
	private Object sagaCompanyKey;
	@JsonProperty("saga_company_name")
	private Object sagaCompanyName;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("saga_company_key")
	public Object getSagaCompanyKey() {
		return sagaCompanyKey;
	}

	@JsonProperty("saga_company_key")
	public void setSagaCompanyKey(Object sagaCompanyKey) {
		this.sagaCompanyKey = sagaCompanyKey;
	}

	@JsonProperty("saga_company_name")
	public Object getSagaCompanyName() {
		return sagaCompanyName;
	}

	@JsonProperty("saga_company_name")
	public void setSagaCompanyName(Object sagaCompanyName) {
		this.sagaCompanyName = sagaCompanyName;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}