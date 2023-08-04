package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.CvSend;

/**
 * CV Service interface
 **/
public interface CvSendService {

    public void save(CvSend cv);
    
    public void delete(String uuid);

	public CvSend getCvByUuid(String uuid);

}
