package com.ciril;

import java.sql.Date;
import java.util.Scanner;

import com.ciril.db.DBExecutioner;
import com.ciril.model.Service;
import com.ciril.model.ServiceType;
import com.ciril.UtilityClass;
/**
 * ServiceUsageUtil class is used for inserting users usage details.
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */

public class ServiceUsageUtil {
	
	private static Scanner scanner = new Scanner(System.in);
	
	//method for userId input from user
	public static String readUserId() 
	{
		boolean temp  = false;
		String userId = null;
		
		while(!temp) {
			System.out.println("Enter user id :");
			userId = scanner.next();
			if (DBExecutioner.getInstance().doesUserExist(userId) == false) {
				System.out.println("The user doesn't exist in system.Please try again");
			}
			else 
				temp = true;
		}		
		return userId;
	}
	//method for service type input from user
	public static ServiceType getServiceType(boolean allowAll) 
	{
		System.out.println("Select service type(1 to 3) : ");
		System.out.println("1. Voice");
		System.out.println("2. Data");
		System.out.println("3. SMS");
		if (allowAll) {
			System.out.println("4. All");
		}
		int serviceId=0;
		while(serviceId<1||serviceId>3)
		{
		 serviceId = scanner.nextInt();
		switch (serviceId) {
		case 1:	
			return ServiceType.VOICE;
		case 2:
			return ServiceType.DATA;
		case 3:
			return ServiceType.SMS;
		case 4:
			if (allowAll) {
				return ServiceType.ALL;
			}
		default:
			System.out.println("Please enter a valid option ");
			
		}
		}
		return null;
	}
	
	public static Date getTimeStamp(){
		Date date = null;
		try{
			date =  UtilityClass.readDate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static void main(String args[]){
		
		boolean option = false;		
		while(!option) {
			String userId = ServiceUsageUtil.readUserId();
			ServiceType serviceType = ServiceUsageUtil.getServiceType(false);
			Date timeStamp = ServiceUsageUtil.getTimeStamp();
			Service service = new Service(userId, serviceType, timeStamp);
			DBExecutioner.getInstance().saveServiceDetails(service);
			System.out.println(" Do you want to enter another record (Y/N): ");
			String choice = scanner.next();
			if(choice.equalsIgnoreCase("y")) {
				option = false;
			}
			else {
				option = true;
				System.out.println(" Exiting the system.");
			}			
		}		
	}
}
