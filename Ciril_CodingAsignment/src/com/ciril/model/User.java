package com.ciril.model;
/**
 * User class is a model plain java class contains getter and setter method for the User parameters.
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */

public class User {
	private String userId;
	private String name;
	private String email;
	private String country;
	private String phoneNumber;

	public User(String name, String email, String country, String phoneNumber) {
		super();
		this.name = name;
		this.email = email;
		this.country = country;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
