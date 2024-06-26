package Entity;

public class InforAsset {
	private String value ; 
	private int quantity ;
	public InforAsset(String value, int quantity) {
		super();
		this.value = value;
		this.quantity = quantity;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
