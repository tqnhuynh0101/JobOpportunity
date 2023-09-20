package com.jot.JobOpportunity.dto.post;

import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.entity.SkillPost;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class PostAutoSearchDto {
    private Long id;
    private String title;
    private Integer ageMax;
    private Integer ageMin;
    private Integer gender;
    private String position;
    private String content;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String unit;
    private String provinceId;
    private String image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        try {
            this.image = Utils.convertToBase64(image);
        } catch (IOException e) {
            this.image = image;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
