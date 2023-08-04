package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.response.DataResponse;

public interface WardsService {
	
	public DataResponse getWardByDistrictCode(String code);

	public String getName(String code);
	
}
