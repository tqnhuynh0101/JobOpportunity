package com.jot.JobOpportunity.controller;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.ProvincesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provinces")
public class ProvincesController {

    private final Logger log = LoggerFactory.getLogger(ProvincesController.class);

    @Autowired
    private ProvincesService provincesService;

    @GetMapping("/get-all")
    public DataResponse getAllProvinces(){
        log.debug("ProvincesController.getAllProvinces()");
        DataResponse res = provincesService.getAllProvinces();
        return res;
    }
}
