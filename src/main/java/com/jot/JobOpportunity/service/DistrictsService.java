package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.response.DataResponse;

public interface DistrictsService {

	public DataResponse getDistrictsByProvinceCode(String code);
	public String getName(String code);
	
}
