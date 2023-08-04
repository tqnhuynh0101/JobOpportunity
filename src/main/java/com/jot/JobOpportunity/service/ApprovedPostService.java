package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.ApprovedPost;

public interface ApprovedPostService {
	
	public void save(ApprovedPost approvedPost);

	public ApprovedPost getByPosterId(Long id);
	
}
