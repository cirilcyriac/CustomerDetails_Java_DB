package com.ciril.model;
/**
 * ServiceType  is an enumeration type for different service types.
 * It prevent the entry of invalid service types other than the defined constants.
 * @author cirilcyriac
 * @version 1.0
 * @since   2015-10-03 
 */
public enum ServiceType {
	VOICE,DATA,SMS, ALL;
	
	public static ServiceType getServiceType(String service) {
		if(service.equals(VOICE.toString())) {
			return VOICE;
		} else if (service.equals(DATA.toString())) {
			return DATA;
		} else if (service.equals(SMS.toString())) {
			return SMS;
		} else{
			return null;
		}
		
	}
}
