package com.jot.JobOpportunity.service;

import java.util.List;

import com.jot.JobOpportunity.dto.skillemployee.SkillEmployeeUpdateDto;
import com.jot.JobOpportunity.entity.response.DataResponse;

public interface SkillEmployeeService {

	public DataResponse getByEmployeeId();

//    DataResponse creatSkillEmployee(List<String> skills);
//
//    DataResponse skillEmployeeUpdateDto(List<SkillEmployeeUpdateDto> listSkill);

    DataResponse saveSkill(List<String> listSkill);
}
