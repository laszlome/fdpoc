package fdpoc.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

import fdpoc.CommonCacheEntry;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.joda.time.DateTime;

/**
 * "Ticket" as defined in FreshDesk
 * 
 * To Do: - use time fields instead String - handle reference objects too - use
 * local mapping for static types (e.g. status etc.)
 * 
 * @author Laszlo.Merenyi
 *
 */
@JsonPropertyOrder({ "cc_emails", "fwd_emails", "reply_cc_emails", "fr_escalated", "spam", "email_config_id",
		"group_id", "priority", "requester_id", "responder_id", "source", "company_id", "status", "subject",
		"to_emails", "product_id", "id", "type", "due_by", "fr_due_by", "is_escalated", "description",
		"description_text", "custom_fields", "created_at", "updated_at", "deleted" })

@XmlRootElement
public class Ticket implements Serializable, CommonCacheEntry {

	private static final long serialVersionUID = 1111L;

	@JsonProperty("cc_emails")
	private List<String> ccEmails = null;

	@JsonProperty("fwd_emails")
	private List<Object> fwdEmails = null;

	@JsonProperty("reply_cc_emails")
	private List<String> replyCcEmails = null;

	@QuerySqlField
	@JsonProperty("fr_escalated")
	private Boolean frEscalated;
	
	@QuerySqlField(index = true)
	@JsonProperty("deleted")
	private Boolean deleted;

	@QuerySqlField
	@JsonProperty("spam")
	private Boolean spam;

	@JsonProperty("email_config_id")
	private Long emailConfigId;

	@QuerySqlField(index = true)
	@JsonProperty("group_id")
	private Long groupId;

	@QuerySqlField(index = true)
	@JsonProperty("priority")
	private Long priority;

	@QuerySqlField(index = true)
	@JsonProperty("requester_id")
	private Long requesterId;

	@QuerySqlField(index = true)
	@JsonProperty("responder_id") 
	private Long responderId; // agent whom the ticket is assigned

	@QuerySqlField(index = true)
	@JsonProperty("source")
	private Long source;

	@QuerySqlField(index = true)
	@JsonProperty("company_id")
	private Object companyId;

	@QuerySqlField(index = true)
	@JsonProperty("status")
	private Long status;

	@QuerySqlField(index = true)
	@JsonProperty("subject")
	private String subject;

	@JsonProperty("to_emails")
	private List<String> toEmails = null;

	@QuerySqlField(index = true)
	@JsonProperty("product_id")
	private Long productId;

	@QuerySqlField(index = true)
	@JsonProperty("id")
	private Long id;

	@QuerySqlField(index = true)
	@JsonProperty("type")
	private String type;

	@QuerySqlField(index = true)
	@JsonProperty("due_by")
	private Date dueBy;

	@QuerySqlField(index = true)
	@JsonProperty("fr_due_by")
	private Date frDueBy;

	@QuerySqlField(index = true)
	@JsonProperty("is_escalated")
	private Boolean isEscalated;

	@QuerySqlField
	@JsonProperty("description")
	private String description;

	@JsonProperty("description_text")
	private String descriptionText;

	@JsonProperty("custom_fields")
	private CustomFields customFields;

	@QuerySqlField(index = true)
	@JsonProperty("created_at")
	private Date createdAt;

	@QuerySqlField(index = true)
	@JsonProperty("updated_at")
	private Date updatedAt;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("cc_emails")
	public List<String> getCcEmails() {
		return ccEmails;
	}

	@JsonProperty("cc_emails")
	public void setCcEmails(List<String> ccEmails) {
		this.ccEmails = ccEmails;
	}

	@JsonProperty("fwd_emails")
	public List<Object> getFwdEmails() {
		return fwdEmails;
	}

	@JsonProperty("fwd_emails")
	public void setFwdEmails(List<Object> fwdEmails) {
		this.fwdEmails = fwdEmails;
	}

	@JsonProperty("reply_cc_emails")
	public List<String> getReplyCcEmails() {
		return replyCcEmails;
	}

	@JsonProperty("reply_cc_emails")
	public void setReplyCcEmails(List<String> replyCcEmails) {
		this.replyCcEmails = replyCcEmails;
	}

	@JsonProperty("fr_escalated")
	public Boolean getFrEscalated() {
		return frEscalated;
	}

	@JsonProperty("fr_escalated")
	public void setFrEscalated(Boolean frEscalated) {
		this.frEscalated = frEscalated;
	}
	
	@JsonProperty("deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	@JsonProperty("deleted")
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	@JsonProperty("spam")
	public Boolean getSpam() {
		return spam;
	}

	@JsonProperty("spam")
	public void setSpam(Boolean spam) {
		this.spam = spam;
	}

	@JsonProperty("email_config_id")
	public Long getEmailConfigId() {
		return emailConfigId;
	}

	@JsonProperty("email_config_id")
	public void setEmailConfigId(Long emailConfigId) {
		this.emailConfigId = emailConfigId;
	}

	@JsonProperty("group_id")
	public Long getGroupId() {
		return groupId;
	}

	@JsonProperty("group_id")
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@JsonProperty("priority")
	public Long getPriority() {
		return priority;
	}

	@JsonProperty("priority")
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	@JsonProperty("requester_id")
	public Long getRequesterId() {
		return requesterId;
	}

	@JsonProperty("requester_id")
	public void setRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	@JsonProperty("responder_id")
	public Long getResponderId() {
		return responderId;
	}

	@JsonProperty("responder_id")
	public void setResponderId(Long responderId) {
		this.responderId = responderId;
	}

	@JsonProperty("source")
	public Long getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(Long source) {
		this.source = source;
	}

	@JsonProperty("company_id")
	public Object getCompanyId() {
		return companyId;
	}

	@JsonProperty("company_id")
	public void setCompanyId(Object companyId) {
		this.companyId = companyId;
	}

	@JsonProperty("status")
	public Long getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(Long status) {
		this.status = status;
	}

	@JsonProperty("subject")
	public String getSubject() {
		return subject;
	}

	@JsonProperty("subject")
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonProperty("to_emails")
	public List<String> getToEmails() {
		return toEmails;
	}

	@JsonProperty("to_emails")
	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}

	@JsonProperty("product_id")
	public Long getProductId() {
		return productId;
	}

	@JsonProperty("product_id")
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("due_by")
	public Date getDueBy() {
		return dueBy;
	}

	@JsonProperty("due_by")
	public void setDueBy(String dueBy) {
		this.dueBy = Date.from(Instant.parse(dueBy));
	}

	@JsonProperty("fr_due_by")
	public Date getFrDueBy() {
		return frDueBy;
	}

	@JsonProperty("fr_due_by")
	public void setFrDueBy(String frDueBy) {
		this.frDueBy = Date.from(Instant.parse(frDueBy));
	}

	@JsonProperty("is_escalated")
	public Boolean getIsEscalated() {
		return isEscalated;
	}

	@JsonProperty("is_escalated")
	public void setIsEscalated(Boolean isEscalated) {
		this.isEscalated = isEscalated;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("description_text")
	public String getDescriptionText() {
		return descriptionText;
	}

	@JsonProperty("description_text")
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	@JsonProperty("custom_fields")
	public CustomFields getCustomFields() {
		return customFields;
	}

	@JsonProperty("custom_fields")
	public void setCustomFields(CustomFields customFields) {
		this.customFields = customFields;
	}

	@JsonProperty("created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = Date.from(Instant.parse(createdAt));
	}

	@JsonProperty("updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@JsonProperty("updated_at")
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = Date.from(Instant.parse(updatedAt));
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public static void main(String[] args) {
		String strDate = "2017-01-25T08:05:07Z"; // original data

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		DateTime dateTime = DateTime.parse(strDate);
		Date javaDateTime = Date.from(Instant.parse(strDate));

		System.out.println("Original: " + strDate + ", converted (joda): " + dateTime.toString()
				+ ", converted (java): " + sdf.format(javaDateTime));

		String strDateBack = dateTime.toString();
		System.out.println("And back to string (JODA): " + strDateBack);

		String strJavaDateBack = sdf.format(javaDateTime);
		System.out.println("And back to string (Java): " + strJavaDateBack);
	}

}