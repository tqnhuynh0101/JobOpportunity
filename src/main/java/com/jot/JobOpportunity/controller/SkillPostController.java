package com.jot.JobOpportunity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.SkillPostService;

@RestController
@RequestMapping("/api/skill-post")
public class SkillPostController {
	
private final Logger log = LoggerFactory.getLogger(SkillController.class);
	
	@Autowired
	private SkillPostService skillPostService;

	@GetMapping("/get-by-post-id/{id}")
	public DataResponse getSkillPostByPostId(@PathVariable String id) {
		DataResponse res = skillPostService.getByPostId(id);
	return res;
	}
}
