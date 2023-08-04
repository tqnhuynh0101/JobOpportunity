package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.service.EmployeePaymentService;

@Service
@Transactional
public class EmployeePaymentServiceImp implements EmployeePaymentService {
    private final Logger log = LoggerFactory.getLogger(EmployeePaymentServiceImp.class);

}
