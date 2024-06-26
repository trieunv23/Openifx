package Entity;

import java.time.LocalDate;

public class User {	
	private String userName ;
	private String passWord ; 
	private String fullName ; 
	private String phoneNumber ; 
	private String email ; 
	private LocalDate dayOfBirth ; 
	private Gender gender ;
	private float asset ; 
	
	public User(String userName, String passWord, String fullName, String phoneNumber, String email, LocalDate dayOfBirth,
			Gender gender , float asset) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.dayOfBirth = dayOfBirth;
		this.gender = gender;
		this.asset = asset ; 
	}	
	
	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(LocalDate dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	

	public float getAsset() {
		return asset;
	}

	public void setAsset(float asset) {
		this.asset = asset;
	}
	
}
