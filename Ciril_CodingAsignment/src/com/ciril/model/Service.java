package com.ciril.model;

import java.sql.Date;
/**
 * Service class is a model plain java class contains getter and setter method for the service parameters.
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */
public class Service {
	
	private String userId;
	private ServiceType serviceType;
	private Date timeStamp;
	
	public Service() {
		
	}
	
	public Service(String userId, ServiceType serviceType, Date timeStamp) {
		super();
		this.userId = userId;
		this.serviceType = serviceType;
		this.timeStamp = timeStamp;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
