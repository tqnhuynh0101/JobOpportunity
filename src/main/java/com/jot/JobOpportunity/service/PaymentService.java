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

    DataResponse getDealDetail(String id);
}
