package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.response.DataResponse;

import java.util.List;

/**
 * Skill Service interface
 **/
public interface SkillService {

	public DataResponse getAllBaseSkill();

	public DataResponse getAll();

	public DataResponse deleteSkillById(String id);

	public DataResponse approveSkillById(String id);

	public Long saveSkill(String skill);
 
}
