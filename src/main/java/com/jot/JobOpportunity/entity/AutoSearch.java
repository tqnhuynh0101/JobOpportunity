package com.jot.JobOpportunity.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "auto_search")
public class AutoSearch extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", columnDefinition = "BIGINT", nullable = false, unique = true)
    private Long accountId;

    @Column(name = "flag", columnDefinition = "BIT DEFAULT 0", nullable = false)
    private Boolean flag;

    @Column( name = "gender", columnDefinition = "BIT DEFAULT 0")
    private Boolean gender;

    @Column (name = "age", columnDefinition = "TINYINT")
    private Integer age;

    @Column(name ="salary", columnDefinition = "DECIMAL")
    private BigDecimal salary;

    @Column(name = "province_code", columnDefinition = "VARCHAR(20)")
    private String provinceCode;


    public AutoSearch() {
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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

