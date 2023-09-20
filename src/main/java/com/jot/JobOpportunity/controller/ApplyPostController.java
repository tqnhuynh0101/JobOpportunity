package com.jot.JobOpportunity.controller;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.ApplyPostService;
import org.aspectj.bridge.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apply-post")
public class ApplyPostController {
    private final Logger log = LoggerFactory.getLogger(ApplyPostController.class);

    @Autowired
    private ApplyPostService applyPostService;

    @PostMapping("/create/{id}")
    public DataResponse createApplyPost(@PathVariable String id){
        log.debug("Request create");
        DataResponse res = applyPostService.createApplyPost(id);
        return res;
    }

    @GetMapping("/get-by-poster")
    public DataResponse getByPosterId(){
        log.debug("Request get by poster id");
        DataResponse res = applyPostService.getByPosterId();
        return res;
    }

    @GetMapping("/get-by-account")
    public DataResponse getByAccountId(){
        log.debug("Request get by account id");
        DataResponse res = applyPostService.getByAccountId();
        return res;
    }

    @GetMapping("/get-my-apply-post")
    public DataResponse getMyApplyPost(){
        log.debug("Request get my apply post");
        DataResponse res = applyPostService.getMyApplyPost();
        return res;
    }

    @PutMapping("/delete-apply-post/{id}")
    public DataResponse deleteMyApplyPost(@PathVariable("id") String id){
        log.debug("Request delete my apply post");
        DataResponse res = applyPostService.deleteMyApplyPost(id);
        return res;
    }

    @PostMapping("/confirm")
    public DataResponse confirm(@RequestParam("id") String id, @RequestParam("type") String type,
                                @RequestParam("message") String message, @RequestParam("date") String date ){
        DataResponse res = applyPostService.confirm(id,message,date,type);
        return res;
    }

}
