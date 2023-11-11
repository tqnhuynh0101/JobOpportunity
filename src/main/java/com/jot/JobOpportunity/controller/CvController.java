package com.jot.JobOpportunity.controller;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.CvService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    private boolean isJpg(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE);
    }

    private boolean isPng(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().equals(MediaType.IMAGE_PNG_VALUE);
    }
    @GetMapping("/get-by-current-user")
    public DataResponse getCvByEmployeeId(){
        log.debug("CvController.getCvEmployeeById()");
        DataResponse res = cvService.getCvByCurrentUser();
        return res;
    }
    @PostMapping("/create")
    public DataResponse createCv(MultipartHttpServletRequest data){
        log.debug("CvController.createCv()");
        MultipartFile file = data.getFile("file");
        String strCv = data.getParameter("cv");
        if (file != null && (isJpg(file) || isPng(file))) {
            DataResponse res = cvService.update(strCv, file);
            return res;
        } else {
            // Return an error response if the file is not JPEG or PNG
            DataResponse errorResponse = new DataResponse();
            errorResponse.setStatus(Constants.ERROR);
            errorResponse.setMessage("File không đúng định dạng");
            return errorResponse;
        }
    }

    @PostMapping("/update")
    public DataResponse updateCv(MultipartHttpServletRequest data){
        log.debug("CvController.update()");
        MultipartFile file = data.getFile("file");
        String strCv = data.getParameter("cv");
        if (file != null && (isJpg(file) || isPng(file))) {
            DataResponse res = cvService.update(strCv, file);
            return res;
        } else {
            // Return an error response if the file is not JPEG or PNG
            DataResponse errorResponse = new DataResponse();
            errorResponse.setStatus(Constants.ERROR);
            errorResponse.setMessage("File không đúng định dạng");
            return errorResponse;
        }
    }

    @GetMapping("/get-cv-view/{uuid}")
    public DataResponse getByUUid(@PathVariable("uuid") String uuid){
        log.debug("CvController.getByUUid()");
        DataResponse res = cvService.getCvByUuid(uuid);
        return res;
    }
}
