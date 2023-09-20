package com.jot.JobOpportunity.controller;


import com.jot.JobOpportunity.dto.autosearch.AutoSearchDto;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.AutoSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auto-search")
public class AutoSearchController {
    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AutoSearchService autoSearchService;

    @PostMapping("/create")
    public DataResponse createAutoSearch(@RequestBody AutoSearchDto autoSearchDto){
        log.debug("Request create");
        DataResponse res = autoSearchService.createAutoSearch(autoSearchDto);
        return res;
    }

    @GetMapping("/get-by-current-user")
    public DataResponse getByCurrentUser(){
        log.debug("Request get by current user");
        DataResponse res = autoSearchService.getAutoSearchByCurrentUser();
        return res;
    }

    @GetMapping("/test")
    public void auto(){
        log.debug("Request get by current user");
        autoSearchService.autoSearch();
        return;
    }
}
