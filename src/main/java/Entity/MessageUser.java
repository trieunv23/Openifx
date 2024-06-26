package Entity;

public class MessageUser {
	private String sender ; 
	private String namesender ; 
	private String receiver ; 
	private String namereceiver ; 
	private String message ; 
	private String daysend ;
	
	public MessageUser(String sender, String namesender, String receiver, String namereceiver, String message,
			String daysend) {
		super();
		this.sender = sender;
		this.namesender = namesender;
		this.receiver = receiver;
		this.namereceiver = namereceiver;
		this.message = message;
		this.daysend = daysend;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getNamesender() {
		return namesender;
	}
	public void setNamesender(String namesender) {
		this.namesender = namesender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getNamereceiver() {
		return namereceiver;
	}
	public void setNamereceiver(String namereceiver) {
		this.namereceiver = namereceiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDaysend() {
		return daysend;
	}
	public void setDaysend(String daysend) {
		this.daysend = daysend;
	}
	@Override
	public String toString() {
		return message + " " + "(" + daysend + ")" ; 
	}
	
	
	
}