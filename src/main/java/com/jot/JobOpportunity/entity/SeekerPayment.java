package com.jot.JobOpportunity.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name ="seeker_payment")
public class SeekerPayment extends BaseEntity{
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", columnDefinition = "BIGINT", nullable = false)
    private Long accountId;

    @Column(name ="type", columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private Integer type;

    @Column(name = "payment_date", columnDefinition = "VARCHAR(50)")
    private String paymentDate;

    public SeekerPayment() {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
