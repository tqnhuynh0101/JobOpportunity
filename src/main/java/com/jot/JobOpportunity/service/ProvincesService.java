package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.response.DataResponse;

/**
 * Provinces Service interface
 **/
public interface ProvincesService {
    public DataResponse getAllProvinces();
    public String getName(String code);
}
