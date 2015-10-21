package com.ciril;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.Scanner;
/**
 * UtilityClass class is used for reading date inputs from user and date format validations .
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */
public class UtilityClass {

	static Scanner scanner = new Scanner(System.in);
	boolean status = false;
	
	//method for validation and input of Date values from user
	public static Date readDate()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Calendar cal = Calendar.getInstance();
		boolean choice = true;
		Date date = null;
		while(choice) {			
			try	{
				choice = false;
				System.out.println(" Enter the year : ");
				int year = Integer.parseInt(br.readLine());
				System.out.println(" Enter the month : ");
				int month = Integer.parseInt(br.readLine());
				System.out.println(" Enter the day : ");
				int day = Integer.parseInt(br.readLine());
				cal.setLenient(false);
				cal.set(year, month-1, day);
				date = new Date(cal.getTime().getTime());
			}catch(IOException e) {
				System.out.println(" Invalid date.");				
			}			
			catch(InputMismatchException e) {
				System.out.println(" Invalid date.");
				choice = true;
			}
			catch(IllegalArgumentException e) {
				System.out.println(" Invalid date.");
				choice = true;
			}catch(Exception e) {
				System.out.println(" Invalid date.");
				choice = true;
			}
		}
		return date;
	}
}
