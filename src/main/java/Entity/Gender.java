package Entity;

public enum Gender {
	Male("Male") , 
	Female("Female"), 
	Other("Other"),
	Null("Null"); 
	
	private final String value ; 
	
	private Gender(String gender) {
		this.value = gender ; 
	}

	public String getValue() {
		return value;
	}
	
	 public static Gender fromString(String value) {
         for (Gender gender : Gender.values()) {
             if (gender.value.equalsIgnoreCase(value)) {
                 return gender;
             }
         }
         throw new IllegalArgumentException("Invalid gender: " + value);
     }
	 
}
