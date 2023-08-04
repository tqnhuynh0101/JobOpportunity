package com.jot.JobOpportunity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Common controller.
 **/
@RestController
@RequestMapping("/api/common")
public class CommonController {

	private final Logger log = LoggerFactory.getLogger(CommonController.class);
}
