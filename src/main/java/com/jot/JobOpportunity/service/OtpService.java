package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.dto.otp.OtpCheck;
import com.jot.JobOpportunity.entity.response.DataResponse;

public interface OtpService {

    public String create(Long accountId);

    public DataResponse check(OtpCheck otpCheck);

    void deleteById(Long id);

    void deleteOtpByAccountId(Long accountId);
}