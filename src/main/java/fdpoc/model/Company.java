package fdpoc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "description", "note", "domains", "created_at", "updated_at", "custom_fields" })
public class Company {

	@QuerySqlField(index = true)
	@JsonProperty("id")
	private Long id;

	@QuerySqlField(index = true)
	@JsonProperty("name")
	private String name;
	
	@QuerySqlField()
	@JsonProperty("description")
	private String description;
	
	@QuerySqlField(index = true)
	@JsonProperty("note")
	private String note;
	
	@QuerySqlField()
	@JsonProperty("domains")
	private List<Object> domains = null;
	
	@QuerySqlField(index = true)
	@JsonProperty("created_at")
	private String createdAt;
	
	@QuerySqlField(index = true)
	@JsonProperty("updated_at")
	private String updatedAt;
	
//	@JsonProperty("custom_fields")
//	private CustomFields customFields; // --> not the same Custom fields as for the Ticket
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("note")
	public String getNote() {
		return note;
	}

	@JsonProperty("note")
	public void setNote(String note) {
		this.note = note;
	}

	@JsonProperty("domains")
	public List<Object> getDomains() {
		return domains;
	}

	@JsonProperty("domains")
	public void setDomains(List<Object> domains) {
		this.domains = domains;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("updated_at")
	public String getUpdatedAt() {
		return updatedAt;
	}

	@JsonProperty("updated_at")
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}


//	@JsonProperty("custom_fields")
//	public CustomFields getCustomFields() {
//		return customFields;
//	}
//
//	@JsonProperty("custom_fields")
//	public void setCustomFields(CustomFields customFields) {
//		this.customFields = customFields;
//	}


}
