package com.jot.JobOpportunity.entity.enumeration;

public enum UnitEnum {
	VND("VNĐ"),
    USD("USD");

	private final String value;

	UnitEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
