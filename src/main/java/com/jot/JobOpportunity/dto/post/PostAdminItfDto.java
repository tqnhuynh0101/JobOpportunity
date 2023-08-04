package com.jot.JobOpportunity.dto.post;

import com.jot.JobOpportunity.entity.enumeration.FormatEnum;
import com.jot.JobOpportunity.entity.enumeration.TypeEnum;
import com.jot.JobOpportunity.entity.enumeration.UnitEnum;

import java.math.BigDecimal;

public interface PostAdminItfDto {
    Long getId();

    String getTitle();

    String getContent();

    Integer getQuantity();

    Integer getAgeMax();

    Integer getAgeMin();

    Integer getGender();

    Float getExperience();

    TypeEnum getType();

    FormatEnum getFormat();

    String getPosition();

    String getRequirement();

    String getBenafit();

    String getDuty();

    boolean getSalary();

    BigDecimal getSalaryMin();

    BigDecimal getSalaryMax();

    UnitEnum getUnit();

    String getCompany();

    String getTel();

    String getEmail();

    String getExpiredDate();

    boolean getFlag();

    Long getPosterId();

    String getAddress();

    String getWardId();

    String getDistrictId();

    String getProvinceId();

    String getImage();

    boolean getIsDel();

    String getCreateBy();

    String getCreateTime();

}
