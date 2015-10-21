package com.ciril;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ciril.db.DBExecutioner;
import com.ciril.model.Service;
import com.ciril.model.ServiceType;

/**
 * ServiceUsageReport class is used for generating The usage report
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */

public class ServiceUsageReport {
	private static Scanner scanner = new Scanner(System.in);
		
	public String readUserId() {
		boolean temp  = false;
		String userId = null;
		while(!temp)
		System.out.println("Enter user identifier of the user :");
		 userId = scanner.nextLine();
		if (DBExecutioner.getInstance().doesUserExist(userId) == false) {
			System.out.println("The user doesn't exist in system.please try again");
		}
		else
			temp=true;
		return userId;
	}
	
	public static void main(String args[]) {
		String userId = ServiceUsageUtil.readUserId();
		System.out.println("Enter begin time of search :");
		Date beginTime = UtilityClass.readDate();
		System.out.println("Enter end time of search :");
		Date endTime = UtilityClass.readDate();
		if (endTime.before(beginTime)) {
			System.out.println("End time is before start time. Hence the system would now exit.");
			System.exit(0);
		}
		ServiceType serviceType = ServiceUsageUtil.getServiceType(true);
		List<Service> serviceDetails = DBExecutioner.getInstance().
				getServiceDetails(serviceType, beginTime, endTime, userId);
		if(serviceDetails.size() == 0) {
			System.out.println("No service usage for the said period. \nThank you");
		} else {
			System.out.println("Service usage summary for the user "+userId 
					+ " between "+ beginTime + " and " + endTime + " is :");
			System.out.println("UserId \t ServiceType \t Date\n");
			for (Service service : serviceDetails) {
				System.out.println(service.getUserId()+"\t"+service.getServiceType().toString()+"\t"+service.getTimeStamp());
			}
		}
	}
}
