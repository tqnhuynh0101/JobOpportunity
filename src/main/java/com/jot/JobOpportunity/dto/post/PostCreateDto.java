package com.jot.JobOpportunity.dto.post;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.jot.JobOpportunity.dto.skillpost.SkillPostDto;
import com.jot.JobOpportunity.entity.enumeration.FormatEnum;
import com.jot.JobOpportunity.entity.enumeration.TypeEnum;
import com.jot.JobOpportunity.entity.enumeration.UnitEnum;

public class PostCreateDto {

	private String title;
	private String content;
	private Integer quantity;
	private Integer ageMax;
	private Integer ageMin;
	private Integer gender;
	private float experience;
	private TypeEnum type;
	private FormatEnum format;
	private String position;
	private String requirement;
	private String benafit;
	private String duty;
	private boolean salary;
	private BigDecimal salaryMin;
	private BigInteger salaryMax;
	private UnitEnum unit;
	private String company;
	private String tel;
	private String email;
	private Long posterId;
	private String address;
	private String wardId;
	private String districtId;
	private String provinceId;
	private String expiredDate;
	private List<String> skillPostList;

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public float getExperience() {
		return experience;
	}

	public void setExperience(float experience) {
		this.experience = experience;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getBenafit() {
		return benafit;
	}

	public void setBenafit(String benafit) {
		this.benafit = benafit;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public boolean isSalary() {
		return salary;
	}

	public void setSalary(boolean salary) {
		this.salary = salary;
	}

	public BigDecimal getSalaryMin() {
		return salaryMin;
	}

	public void setSalaryMin(BigDecimal salaryMin) {
		this.salaryMin = salaryMin;
	}

	public BigInteger getSalaryMax() {
		return salaryMax;
	}

	public void setSalaryMax(BigInteger salaryMax) {
		this.salaryMax = salaryMax;
	}

	public UnitEnum getUnit() {
		return unit;
	}

	public void setUnit(UnitEnum unit) {
		this.unit = unit;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPosterId() {
		return posterId;
	}

	public void setPosterId(Long posterId) {
		this.posterId = posterId;
	}

	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	public FormatEnum getFormat() {
		return format;
	}

	public void setFormat(FormatEnum format) {
		this.format = format;
	}

	public List<String> getSkillPostList() {
		return skillPostList;
	}

	public void setSkillPostList(List<String> skillPostList) {
		this.skillPostList = skillPostList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWardId() {
		return wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
}
