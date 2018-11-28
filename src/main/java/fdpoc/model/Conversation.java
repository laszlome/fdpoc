package fdpoc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
@SuppressWarnings("serial")
@JsonPropertyOrder({ "body", "body_text", "id", "incoming", "private", "user_id", "support_email", "source",
		"ticket_id", "to_emails", "from_email", "cc_emails", "bcc_emails", "created_at", "updated_at", "attachments" })
public class Conversation implements Serializable {

	@JsonProperty("body")
	private String body;
	@JsonProperty("body_text")
	private String bodyText;
	@JsonProperty("id")
	private Long id;
	@JsonProperty("incoming")
	private Boolean incoming;
	@JsonProperty("private")
	private Boolean _private;
	@JsonProperty("user_id")
	private Long userId;
	@JsonProperty("support_email")
	private String supportEmail;
	@JsonProperty("source")
	private Long source;
	@JsonProperty("ticket_id")
	private Long ticketId;
	@JsonProperty("to_emails")
	private List<String> toEmails = null;
	@JsonProperty("from_email")
	private String fromEmail;
	@JsonProperty("cc_emails")
	private List<String> ccEmails = null;
	@JsonProperty("bcc_emails")
	private List<Object> bccEmails = null;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("updated_at")
	private String updatedAt;
	@JsonProperty("attachments")
	private List<Object> attachments = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("body")
	public String getBody() {
		return body;
	}

	@JsonProperty("body")
	public void setBody(String body) {
		this.body = body;
	}

	@JsonProperty("body_text")
	public String getBodyText() {
		return bodyText;
	}

	@JsonProperty("body_text")
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("incoming")
	public Boolean getIncoming() {
		return incoming;
	}

	@JsonProperty("incoming")
	public void setIncoming(Boolean incoming) {
		this.incoming = incoming;
	}

	@JsonProperty("private")
	public Boolean getPrivate() {
		return _private;
	}

	@JsonProperty("private")
	public void setPrivate(Boolean _private) {
		this._private = _private;
	}

	@JsonProperty("user_id")
	public Long getUserId() {
		return userId;
	}

	@JsonProperty("user_id")
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonProperty("support_email")
	public String getSupportEmail() {
		return supportEmail;
	}

	@JsonProperty("support_email")
	public void setSupportEmail(String supportEmail) {
		this.supportEmail = supportEmail;
	}

	@JsonProperty("source")
	public Long getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(Long source) {
		this.source = source;
	}

	@JsonProperty("ticket_id")
	public Long getTicketId() {
		return ticketId;
	}

	@JsonProperty("ticket_id")
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	@JsonProperty("to_emails")
	public List<String> getToEmails() {
		return toEmails;
	}

	@JsonProperty("to_emails")
	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}

	@JsonProperty("from_email")
	public String getFromEmail() {
		return fromEmail;
	}

	@JsonProperty("from_email")
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	@JsonProperty("cc_emails")
	public List<String> getCcEmails() {
		return ccEmails;
	}

	@JsonProperty("cc_emails")
	public void setCcEmails(List<String> ccEmails) {
		this.ccEmails = ccEmails;
	}

	@JsonProperty("bcc_emails")
	public List<Object> getBccEmails() {
		return bccEmails;
	}

	@JsonProperty("bcc_emails")
	public void setBccEmails(List<Object> bccEmails) {
		this.bccEmails = bccEmails;
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

	@JsonProperty("attachments")
	public List<Object> getAttachments() {
		return attachments;
	}

	@JsonProperty("attachments")
	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
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