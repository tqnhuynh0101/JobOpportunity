package com.jot.JobOpportunity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.SkillService;

/**
 * Skill controller.
 **/
@RestController
@RequestMapping("/api/skill")
public class SkillController {

	private final Logger log = LoggerFactory.getLogger(SkillController.class);
	
	@Autowired
	private SkillService skillService;
	
	@GetMapping("/base-skill")
	public DataResponse getAllBaseSkill() {
		log.debug("SkillController.getAllBaseSkill()");
		DataResponse res = skillService.getAllBaseSkill();
		return res;
		
	}

	@GetMapping("/get-all")
	public DataResponse getALlSkill(){
		log.debug("SkillController.getAllSkill()");
		DataResponse res = skillService.getAll();
		return res;
	}

	@PutMapping("/approve/{id}")
	public DataResponse approveSkillById(@PathVariable("id") String id){
		log.debug("SkillController.getAllSkill()");
		DataResponse res = skillService.approveSkillById(id);
		return res;
	}

	@DeleteMapping("/delete/{id}")
	public DataResponse deleteSkillById(@PathVariable("id") String id){
		log.debug("SkillController.getAllSkill()");
		DataResponse res = skillService.deleteSkillById(id);
		return res;
	}
}
