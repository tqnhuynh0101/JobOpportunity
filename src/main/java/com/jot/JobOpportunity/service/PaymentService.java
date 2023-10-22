package com.jot.JobOpportunity.service;


import com.jot.JobOpportunity.entity.response.DataResponse;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Payment Service interface
 **/
public interface PaymentService {

    public DataResponse payment(BigDecimal amount, String content, String type, Integer quantity, Integer quantityMonth);
    DataResponse getIncome();
    public void paySuccess(String id);
    DataResponse getDealDetail(String id);
}
