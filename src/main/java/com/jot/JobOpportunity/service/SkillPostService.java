package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.SkillPost;
import com.jot.JobOpportunity.entity.response.DataResponse;

public interface SkillPostService {
	
	public DataResponse getByPostId(String id);
	
	public void saveSkillPost(SkillPost skillPost);
}
