package com.jot.JobOpportunity.service;

import org.springframework.web.multipart.MultipartFile;

import com.jot.JobOpportunity.entity.response.DataResponse;

/**
 * CV Service interface
 **/
public interface CvService {

    public DataResponse getCvByCurrentUser();
    
    public DataResponse save(String cv, MultipartFile file);

	public DataResponse getCvByUuid(String uuid);

}
