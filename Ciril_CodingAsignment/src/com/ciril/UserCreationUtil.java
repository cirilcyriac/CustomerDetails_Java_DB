package com.ciril;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ciril.db.DBExecutioner;
import com.ciril.model.User;
/**
 * UserCreationUtil class is used for user profile creation .
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */
public class UserCreationUtil {
	
	Scanner scanner = new Scanner(System.in);
	
	//regular expressions for email, name and phone number validations
	private static final String nameStringPattern = "[A-Za-z ]+";
	Pattern namePattern = Pattern.compile(nameStringPattern);
	
	private static final String emailStringPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
	Pattern emailPattern = Pattern.compile(emailStringPattern);
	
	public static final String phoneNumberPattern = "\\D*([2-9]\\d{2})(\\-?)([2-9]\\d{2})(\\D*)(\\d{4})\\D*";
	Pattern phonePattern = Pattern.compile(phoneNumberPattern);
	// method for username validation	
	public String readName(){
		boolean match = false;
		while (match == false) {
			System.out.println("Enter the name of the user: ");
			String name = scanner.nextLine();
			Matcher matcher = namePattern.matcher(name);
			if (matcher.matches()) {
				return name;
			}
			System.out.println("Name doesn't match the standard specified. Please retry ...");
		}
		return null;
	}
	//method for email format validation
	public String readEmail(){
		boolean match = false;
		while (match == false) {
			System.out.println("Enter the email of the user :");
			String email = scanner.nextLine();
			Matcher matcher = emailPattern.matcher(email);
			if (matcher.matches()) {
				
				String temp = DBExecutioner.searchEmail(email);
				if(temp==null) {
					match = true;
					return email;					
				}
				else
					System.out.println(" User with the same email id exists. Please enter different email. ");
				
			} else {
				System.out.println("Email doesn't match the standard specified. Please retry ...");
			}						
		}
		return null;
	}
	// phone number format validation method
	public String readPhoneNumber(){
		boolean match = false;
		while(match == false) {
			System.out.println("Enter the phone number of the user :");
			String phoneNumber = scanner.nextLine();
			Matcher matcher = phonePattern.matcher(phoneNumber);
			if(matcher.matches()) {
				return phoneNumber;
			}
			System.out.println("Please enter the phone number in xxx-xxx-xxxx  pattern. Only numbers and dashes permitted. ");
		}
		return null;
	}
	
	public String readCountry(){
		System.out.println("Enter the country of the user :");
		String country = scanner.nextLine();
		return country;
	}
	
	public static void main(String args[]){
		UserCreationUtil userCreationUtil = new UserCreationUtil();
		String name = userCreationUtil.readName();
		String email = userCreationUtil.readEmail();				
		String phoneNumber = userCreationUtil.readPhoneNumber();
		String country = userCreationUtil.readCountry();
		User user = new User(name,email,country,phoneNumber);
		@SuppressWarnings("static-access")
		String userId = DBExecutioner.getInstance().insertUser(user);
		if (userId != null) {
			System.out.println(" Created user with user id = "+userId);
		}
		System.out.println(name);
	}
}
