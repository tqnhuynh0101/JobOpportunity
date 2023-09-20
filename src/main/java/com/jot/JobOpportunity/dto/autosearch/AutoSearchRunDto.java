package com.jot.JobOpportunity.dto.autosearch;

import com.jot.JobOpportunity.entity.SkillEmployee;

import java.math.BigDecimal;
import java.util.List;

public class AutoSearchRunDto {

    private Long accountId;
    private Integer age;
    private Integer gender;
    private String provinceCode;
    private BigDecimal salary;
    private String pos;
    private String email;
    private List<SkillEmployee> skills;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        if(gender == true)
            this.gender = 1;
        else
            this.gender = 0;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<SkillEmployee> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillEmployee> skills) {
        this.skills = skills;
    }
}
