package com.jot.JobOpportunity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.WardsService;

/**
 * Wards controller.
 **/
@RestController
@RequestMapping("/api/wards")
public class WardsController {
private final Logger log = LoggerFactory.getLogger(DistrictsController.class);
	
	@Autowired
	private WardsService wardsService;
	
	@GetMapping("/get-by-district-code/{code}")
	public DataResponse getWardByDistrictCode(@PathVariable("code") String code) {
		log.debug("WardsController.getWardByDistrictCode()");
		DataResponse res = wardsService.getWardByDistrictCode(code);
		return res;
		
	}
}
