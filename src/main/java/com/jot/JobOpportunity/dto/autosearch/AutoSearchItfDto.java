package com.jot.JobOpportunity.dto.autosearch;

import java.math.BigDecimal;

public interface AutoSearchItfDto {
    Long getId();
    Long getAccountId();
    int getAge();
    boolean getFlag();
    boolean getGender();
    String getProvince();
    BigDecimal getSalary();
}
