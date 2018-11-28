package fdpoc.model;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Simple representation of ticket priority values 
 * 
 * @author Laszlo.Merenyi
 *
 */
@SuppressWarnings("serial")
public class Priority implements Serializable {
	@QuerySqlField(index = true)
	private Long id;
	
	@QuerySqlField
	private String name;

	public Priority(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
