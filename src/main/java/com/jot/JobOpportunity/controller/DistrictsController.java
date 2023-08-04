package com.jot.JobOpportunity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.DistrictsService;

/**
 * Skill controller.
 **/
@RestController
@RequestMapping("/api/districts")
public class DistrictsController {

	private final Logger log = LoggerFactory.getLogger(DistrictsController.class);
	
	@Autowired
	private DistrictsService districtsService;
	
	@GetMapping("/get-by-province-code/{code}")
	public DataResponse getDistrictsByProvinceCode(@PathVariable("code") String code) {
		log.debug("DistrictsController.getDistrictsByProvinceCode()");
		DataResponse res = districtsService.getDistrictsByProvinceCode(code);
		return res;
		
	}
}
