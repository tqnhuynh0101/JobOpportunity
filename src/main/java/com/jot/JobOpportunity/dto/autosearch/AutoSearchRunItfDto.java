package com.jot.JobOpportunity.dto.autosearch;

import java.math.BigDecimal;

public interface AutoSearchRunItfDto {
    Long getAccountId();
    Integer getAge();
    Integer getGender();
    String getProvinceCode();
    BigDecimal getSalary();
    String getPos();
}
