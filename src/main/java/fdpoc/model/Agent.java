package fdpoc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fdpoc.CommonCacheEntry;
import fdpoc.model.Contact;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "available", "occasional", "id", "signature", "ticket_scope", "created_at", "updated_at",
		"available_since", "contact" })
public class Agent implements Serializable, CommonCacheEntry {

	private static final long serialVersionUID = 1115L;
	
	@JsonProperty("available")
	private Boolean available;
	
	@JsonProperty("occasional")
	private Boolean occasional;
	
	@QuerySqlField(index = true)
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("signature")
	private String signature;
	
	@JsonProperty("ticket_scope")
	private Long ticketScope;

	@QuerySqlField
	@JsonProperty("created_at")
	private Date createdAt;
	
	@QuerySqlField(index = true)
	@JsonProperty("updated_at")
	private Date updatedAt;

	@QuerySqlField(index = true)
	@JsonProperty("available_since")
	private Date availableSince;

	@QuerySqlField(index = true)
	@JsonProperty("contact")
	private Contact contact;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("available")
	public Boolean getAvailable() {
		return available;
	}

	@JsonProperty("available")
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@JsonProperty("occasional")
	public Boolean getOccasional() {
		return occasional;
	}

	@JsonProperty("occasional")
	public void setOccasional(Boolean occasional) {
		this.occasional = occasional;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("signature")
	public String getSignature() {
		return signature;
	}

	@JsonProperty("signature")
	public void setSignature(String signature) {
		this.signature = signature;
	}

	@JsonProperty("ticket_scope")
	public Long getTicketScope() {
		return ticketScope;
	}

	@JsonProperty("ticket_scope")
	public void setTicketScope(Long ticketScope) {
		this.ticketScope = ticketScope;
	}

	@JsonProperty("created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@JsonProperty("updated_at")
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonProperty("available_since")
	public Object getAvailableSince() {
		return availableSince;
	}

	@JsonProperty("available_since")
	public void setAvailableSince(Date availableSince) {
		this.availableSince = availableSince;
	}

	@JsonProperty("contact")
	public Contact getContact() {
		return contact;
	}

	@JsonProperty("contact")
	public void setContact(Contact contact) {
		this.contact = contact;
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