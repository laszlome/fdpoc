package fdpoc.model;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Simple status representation
 * 
 * @author Laszlo.Merenyi
 */
@SuppressWarnings("serial")
public class Status implements Serializable {

	@QuerySqlField(index = true)
	long id;

	@QuerySqlField
	private String status;

	public Status(Long id, String status) {
		this.id = id;
		this.status = status;
	}
}