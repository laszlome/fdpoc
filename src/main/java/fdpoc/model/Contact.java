package fdpoc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "active", "email", "job_title", "language", "last_login_at", "mobile", "name", "phone",
		"time_zone", "created_at", "updated_at" })
public class Contact implements Serializable {

	/**
	 * Contact
	 */
	private static final long serialVersionUID = 1119L;

	@JsonProperty("active")
	private Boolean active;

	@QuerySqlField
	@JsonProperty("email")
	private String email;
	
	@QuerySqlField
	@JsonProperty("job_title")
	private String jobTitle;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("last_login_at")
	private String lastLoginAt;
	
	@JsonProperty("mobile")
	private String mobile;
	
	@QuerySqlField(index = true)
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("phone")
	private String phone;
	
	@JsonProperty("time_zone")
	private String timeZone;
	
	//@QuerySqlField(index = true)
	@JsonProperty("created_at")
	private Date createdAt;
	
	//@QuerySqlField(index = true)
	@JsonProperty("updated_at")
	private Date updatedAt;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("active")
	public Boolean getActive() {
		return active;
	}

	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("job_title")
	public String getJobTitle() {
		return jobTitle;
	}

	@JsonProperty("job_title")
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@JsonProperty("language")
	public String getLanguage() {
		return language;
	}

	@JsonProperty("language")
	public void setLanguage(String language) {
		this.language = language;
	}

	@JsonProperty("last_login_at")
	public String getLastLoginAt() {
		return lastLoginAt;
	}

	@JsonProperty("last_login_at")
	public void setLastLoginAt(String lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	@JsonProperty("mobile")
	public String getMobile() {
		return mobile;
	}

	@JsonProperty("mobile")
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("phone")
	public String getPhone() {
		return phone;
	}

	@JsonProperty("phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty("time_zone")
	public String getTimeZone() {
		return timeZone;
	}

	@JsonProperty("time_zone")
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}