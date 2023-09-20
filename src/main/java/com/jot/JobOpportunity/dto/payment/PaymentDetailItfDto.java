package com.jot.JobOpportunity.dto.payment;

import java.math.BigDecimal;

public interface PaymentDetailItfDto {
    String getCreateTime();
    String getCreateBy();
    BigDecimal getAmount();
    Long getEmployeeId();
}
