package com.jot.JobOpportunity.controller;

import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * payment controller.
 **/
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public DataResponse payment(@RequestParam("amount")BigDecimal amount,@RequestParam("content") String content,
                                @RequestParam("type")String type,@RequestParam("quantity") Integer quantity,
                                @RequestParam("quantityMonth") Integer quantityMonth){
        DataResponse res = paymentService.payment(amount,content,type,quantity,quantityMonth);
        return res;
    }

    @GetMapping("/income")
    public DataResponse getIncome(){
        DataResponse res = paymentService.getIncome();
        return res;
    }

    @GetMapping("/deal-detail/{id}")
    public DataResponse getDealDetail(@PathVariable("id") String id){
        DataResponse res = paymentService.getDealDetail(id);
        return res;
    }

}
