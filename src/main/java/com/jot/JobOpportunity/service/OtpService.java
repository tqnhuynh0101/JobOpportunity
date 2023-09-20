package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.entity.response.DataResponse;

public interface OtpService {

    public String create(Long accountId);

    public DataResponse check(String otp, String id);

//    void deleteById(Long id);
//
//    void deleteOtpByAccountId(Long accountId);
}