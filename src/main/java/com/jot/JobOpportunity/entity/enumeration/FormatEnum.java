package com.jot.JobOpportunity.entity.enumeration;
/**
 * enumeration for format POST
 */
public enum FormatEnum {
	ONLINE("Online"),
	OFFLINE("Offline");
	
	private final String value;

	FormatEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
	
}
