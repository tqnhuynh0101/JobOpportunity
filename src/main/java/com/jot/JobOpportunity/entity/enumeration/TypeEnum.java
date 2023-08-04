package com.jot.JobOpportunity.entity.enumeration;
/**
 * enumeration for type POST
 */
public enum TypeEnum {
	PART_TIME("Part-time"),
    FULL_TIME("Full-time");

	
	private final String value;

	TypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
