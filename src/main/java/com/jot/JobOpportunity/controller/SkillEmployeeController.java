package com.jot.JobOpportunity.controller;

import com.jot.JobOpportunity.dto.skillemployee.SkillEmployeeUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.SkillEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/skill-employee")
public class SkillEmployeeController {

	private final Logger log = LoggerFactory.getLogger(SkillEmployeeController.class);
	
	@Autowired
	private SkillEmployeeService skillEmployeeService;
	
	@GetMapping("/get-by-employee-id")
	public DataResponse getByEmployeeId() {
		log.debug("SkillEmployeeController.getEmployeeById()");
		DataResponse res = skillEmployeeService.getByEmployeeId();
		return res;
	}

//	@PostMapping("/create")
//	public DataResponse createSkillEmployee(@RequestBody List<String> skills){
//		log.debug("SkillEmployeeController.createSkillEmployee()");
//		DataResponse res = skillEmployeeService.creatSkillEmployee(skills);
//		return res;
//	}
//
//	@PutMapping("/update-skill-employee")
//	public DataResponse updateSkillEmployee(@RequestBody List<SkillEmployeeUpdateDto> listSkill){
//		log.debug("SkillEmployeeController.updateSkillEmployee()");
//		DataResponse res = skillEmployeeService.skillEmployeeUpdateDto(listSkill);
//		return res;
//	}

	@PutMapping("/save")
	public DataResponse saveSkillEmployee(@RequestBody List<String> listSkill){
		log.debug("SkillEmployeeController.saveSkillEmployee()");
		DataResponse res = skillEmployeeService.saveSkill(listSkill);
		return res;
	}
}
