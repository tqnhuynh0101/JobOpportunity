package com.jot.JobOpportunity.dto.post;

import java.math.BigDecimal;

public interface PostAutoSearchItfDto {

    Long getId();
    Integer getAgeMax();
    Integer getAgeMin();
    Integer getGender();
    String getPosition();
    BigDecimal getSalaryMin();
    BigDecimal getSalaryMax();
    String getProvinceId();
    String getTitle();
    String getImage();
    String getContent();
    String getUnit();
}
