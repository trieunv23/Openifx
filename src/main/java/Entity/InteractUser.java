package Entity;

public class InteractUser {
	private String usernameinteract ;
	private String fullnameinteract ; 
	private String timeinteract ;
	public InteractUser(String usernameinteract, String fullnameinteract, String timeinteract) {
		super();
		this.usernameinteract = usernameinteract;
		this.fullnameinteract = fullnameinteract;
		this.timeinteract = timeinteract;
	}
	public String getUsernameinteract() {
		return usernameinteract;
	}
	public void setUsernameinteract(String usernameinteract) {
		this.usernameinteract = usernameinteract;
	}
	public String getFullnameinteract() {
		return fullnameinteract;
	}
	public void setFullnameinteract(String fullnameinteract) {
		this.fullnameinteract = fullnameinteract;
	}
	public String getTimeinteract() {
		return timeinteract;
	}
	public void setTimeinteract(String timeinteract) {
		this.timeinteract = timeinteract;
	}
	
	
}
