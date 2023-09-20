package com.jot.JobOpportunity.dto.payment;

import java.math.BigDecimal;

public interface PaymentChartItfDto {
    String getMonth();
    String getYear();
    BigDecimal getTotalAmount();
}
