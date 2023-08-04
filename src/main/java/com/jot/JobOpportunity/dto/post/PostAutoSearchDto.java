package com.jot.JobOpportunity.dto.post;

import com.jot.JobOpportunity.entity.SkillPost;

import java.math.BigDecimal;
import java.util.List;

public class PostAutoSearchDto {
    private Long id;
    private Integer ageMax;
    private Integer ageMin;
    private Integer gender;
    private String position;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String provinceId;
    private List<SkillPost> skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(BigDecimal salaryMin) {
        this.salaryMin = salaryMin;
    }

    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(BigDecimal salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<SkillPost> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillPost> skills) {
        this.skills = skills;
    }
}
