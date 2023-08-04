package com.jot.JobOpportunity.controller;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.CvService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/api/cv")
public class CvController {
    private final Logger log = LoggerFactory.getLogger(CvController.class);

    @Autowired
    private CvService cvService;

    @GetMapping("/get-by-current-user")
    public DataResponse getCvByEmployeeId(){
        log.debug("CvController.getCvEmployeeById()");
        DataResponse res = cvService.getCvByCurrentUser();
        return res;
    }

    @PostMapping("/save")
    public DataResponse saveCv(MultipartHttpServletRequest data){
        log.debug("CvController.createCv()");
        MultipartFile file = data.getFile("file");
        String strCv = data.getParameter("cv");
        DataResponse res = cvService.save(strCv, file);
        return res;
    }

    @GetMapping("/get-cv-view/{uuid}")
    public DataResponse getByUUid(@PathVariable("uuid") String uuid){
        DataResponse res = new DataResponse();
        res = cvService.getCvByUuid(uuid);
        return res;
    }
}
