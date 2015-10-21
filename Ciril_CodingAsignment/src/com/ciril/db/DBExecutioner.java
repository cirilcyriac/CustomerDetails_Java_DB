package com.ciril.db;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ciril.model.Service;
import com.ciril.model.ServiceType;
import com.ciril.model.User;
/**
 * DBExecutioner class is used for database connection through JDBC api .
 * Here we are using a singleton design pattern for limiting the object creation to a single instance
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */
public class DBExecutioner {
	
	static String JDBC_DRIVER;
	static String DB_URL;
	static String USER;
	static String PASS;
	
	
	private static DBExecutioner dbExecutioner = new DBExecutioner(); 

	//Private constructor for achieving singleton design pattern.
	private DBExecutioner()  
	
	{
		try {
			try {
				getProprtyValues();
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't find DB drivers. System would exit now.");
			System.exit(0);
		}
		if (System.getProperty("driver") != null) {
			JDBC_DRIVER = System.getProperty("driver");
		}
		if (System.getProperty("dbURL") != null) {
			DB_URL = System.getProperty("dbURL");
		}
		//  Database credentials
		if (System.getProperty("dbUser") != null) {
			USER = System.getProperty("dbUser");
		}
		if (System.getProperty("dbPass") != null) {
			PASS = System.getProperty("dbPass"); 
		}
	}


	private static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		return conn;
	}
	//method for User Id Generation
	private static String insertUserCommit(Connection conn,User user) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) numUsers from USER_PROFILE";
		ResultSet rs = stmt.executeQuery(sql);
		
		/*User Ids are formated to 10 digits. 
		 * since userId may contains characters we can not use auto_incriment property of databse  for id generation.
		 */
	    Long numUsers = 0l;
		if(rs.next()) {
			numUsers = rs.getLong("numUsers");
		}
		numUsers +=1;
		user.setUserId(String.format("%010d", numUsers));
		sql = "INSERT INTO USER_PROFILE(USER_ID, USER_NAME, USER_EMAIL, USER_PHONE_NUMBER, USER_COUNTRY) VALUES ('"+
				user.getUserId()+"','"+user.getName()+"','"+user.getEmail()+"','"+user.getPhoneNumber()+"','"+user.getCountry()+"')";
		int res = stmt.executeUpdate(sql);
		System.out.println(res+" rows inserted");
		stmt.close();
		//conn.commit();
		return user.getUserId();
	}


	public static DBExecutioner getInstance() {
		return dbExecutioner;
	}
	
	//method for reading db credentials from property file
	public  static void  getProprtyValues() throws FileNotFoundException
	{  
		Properties prop = new Properties();
		
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("DBCredentials.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		 JDBC_DRIVER = prop.getProperty("Driver_name");
		 DB_URL = prop.getProperty("DB_URL");
		 USER = prop.getProperty("userName");
		 PASS = prop.getProperty("password");
		
	}
	// method for fetching service usage details from db
	public List<Service> getServiceDetails(ServiceType serviceType, Date beginDate,
			Date endDate, String userId) {
		List<Service> serviceDetails = new ArrayList<Service>();
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			System.out.println("Creating statement...");
			String sql;
			sql = "SELECT USER_ID,SERVICE_TYPE, SERVICE_TIMESTAMP FROM " +
					"USER_SERVICE WHERE SERVICE_TIMESTAMP >= ? and " +
					"SERVICE_TIMESTAMP <= ? AND USER_ID = ?";
			if (serviceType.equals(ServiceType.ALL) == false) {
				sql = sql + " AND SERVICE_TYPE = '"+serviceType.toString()+"'";
			}
			stmt = conn.prepareStatement(sql);
			stmt.setDate(1, beginDate);
			stmt.setDate(2, endDate);
			stmt.setString(3, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Service service = new Service();
				service.setUserId(rs.getString(1));
				service.setServiceType(ServiceType.getServiceType(rs.getString(2)));
				service.setTimeStamp(rs.getDate(3));
				serviceDetails.add(service);
			}
		} catch(Exception exp) {
			exp.printStackTrace();
		} finally{
			try {
				if (stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return serviceDetails;
	}
	//Method for entering service details to db
	public void saveServiceDetails(Service service) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			System.out.println("Creating statement...");
			String sql;
			sql = "INSERT INTO USER_SERVICE(USER_ID,SERVICE_TYPE, SERVICE_TIMESTAMP) VALUES (?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, service.getUserId());
			stmt.setString(2, service.getServiceType().toString());
			stmt.setDate(3, service.getTimeStamp());
			int result = stmt.executeUpdate();
			if (result != 0) {
				System.out.println("Created "+result+" service records");
			}
		} catch(Exception exp) {
			exp.printStackTrace();
		} finally{
			try {
				if (stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// Method for checking the existence of user in db
	public boolean doesUserExist(String userId) {
		Statement stmt = null;
		Connection conn = null;
		boolean userExists = false;
		try {
			conn = getConnection();
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT 1 FROM USER_PROFILE WHERE USER_ID = '"+userId.trim()+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				userExists = true;
			}
		} catch(Exception exp) {
			exp.printStackTrace();
		} finally{
			try {
				if (stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userExists;
	}
	//method for inserting user details to databse
	public static String insertUser(User user) {
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT 1 FROM USER_PROFILE WHERE USER_EMAIL = '"+user.getEmail()+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("User with same email exists. Enter a different email id.");
				return null;
			} else {
				return insertUserCommit(conn,user);
			}
		} catch(SQLException exp) {
			exp.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			try {
				if (stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//method for searching duplicate emails.
	public static String searchEmail(String email)
	{
		Statement stmt = null;
		Connection conn = null;
		try{
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM USER_PROFILE WHERE USER_EMAIL = '"+email.trim()+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return rs.getString("USER_NAME");
			}else
				return null;
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e){
			
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				
			}			
		}
		return null;
	}
}
