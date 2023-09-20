package com.jot.JobOpportunity.dto.autosearch;

import java.math.BigDecimal;

public interface AutoSearchRunItfDto {
    Long getAccountId();
    Integer getAge();
    boolean getGender();
    String getProvinceCode();
    BigDecimal getSalary();
    String getPos();
    String getEmail();
    String getContent();
}
