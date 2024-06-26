package Exception;

import java.sql.SQLException;

public class SQLException1 extends SQLException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message ;

	public SQLException1(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 
	
	
}
