package com.jot.JobOpportunity.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author ACER
 * pay ment của hệ thống
 */

@Entity
@Table(name = "payment")
public class Payment extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", columnDefinition = "BIGINT", nullable = false)
    private Long employeeId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "content", columnDefinition = "VARCHAR(500)")
    private String content;

    @Column(name ="month", columnDefinition = "VARCHAR(45)", nullable = false)
    private String month;

    @Column(name ="year", columnDefinition = "VARCHAR(45)", nullable = false)
    private String year;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
