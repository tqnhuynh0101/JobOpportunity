package com.jot.JobOpportunity.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_payment")
public class EmployeePayment extends BaseEntity{

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", columnDefinition = "BIGINT", nullable = false)
    private Long accountId;

    @Column(name = "payment_date", columnDefinition = "VARCHAR(50)")
    private String paymentDate;

    @Column(name = "amount", columnDefinition = "DECIMAL DEFAULT 0", nullable = false)
    private BigDecimal amount;

    public EmployeePayment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
