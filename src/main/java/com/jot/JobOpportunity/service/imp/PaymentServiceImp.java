package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.service.PaymentService;

/**
 * Payment service implement
 *
 */

@Service
@Transactional
public class PaymentServiceImp implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImp.class);

}
