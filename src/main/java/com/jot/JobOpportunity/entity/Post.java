package com.jot.JobOpportunity.entity;

import javax.persistence.*;

import com.jot.JobOpportunity.entity.enumeration.FormatEnum;
import com.jot.JobOpportunity.entity.enumeration.TypeEnum;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Post của hệ thống
 **/
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", columnDefinition = "VARCHAR(100)", nullable = false)
	private String title;
	
	@Column(name = "image", columnDefinition = "VARCHAR(200)", nullable = false)
	private String image;
	
	@Column(name = "address", columnDefinition = "VARCHAR(500)", nullable = false)
	private String address;
	
	@Column(name = "ward_id", columnDefinition = "VARCHAR(20)", nullable = false)
	private String wardId;

	@Column(name = "district_id", columnDefinition = "VARCHAR(20)", nullable = false)
	private String districtId;

	@Column(name = "province_id", columnDefinition = "VARCHAR(20)", nullable = false)
	private String provinceId;

	@Column(name = "content", columnDefinition = "VARCHAR(500)", nullable = false)
	private String content;

	@Column(name = "quantity", columnDefinition = "INT DEFAULT 1", nullable = false)
	private Integer quantity;

	@Column(name = "age_max", columnDefinition = "TINYINT(4) DEFAULT 50", nullable = false)
	private Integer ageMax;

	@Column(name = "age_min", columnDefinition = "TINYINT(4) DEFAULT 18", nullable = false)
	private Integer ageMin;

	@Column(name = "gender", columnDefinition = "TINYINT(4) DEFAULT 0", nullable = false)
	private Integer gender;

	@Column(name = "experience", columnDefinition = "FLOAT(4,2) DEFAULT 1", nullable = false)
	private Float experience;

	@Column(name = "type", columnDefinition = "VARCHAR(50)", nullable = false)
	private String type;

	@Column(name = "format", columnDefinition = "VARCHAR(50)", nullable = false)
	private String format;

	@Column(name = "position", columnDefinition = "VARCHAR(50)")
	private String position;

	@Column(name = "requirement", columnDefinition = "VARCHAR(500)")
	private String requirement;

	@Column(name = "benafit", columnDefinition = "VARCHAR(500)")
	private String benafit;

	@Column(name = "duty", columnDefinition = "VARCHAR(500)")
	private String duty;

	@Column(name = "salary", columnDefinition = "BIT DEFAULT 0", nullable = false)
	private boolean salary;

	@Column(name = "salary_min", columnDefinition = "DECIMAL DEFAULT 0", nullable = false)
	private BigDecimal salaryMin;

	@Column(name = "salary_max", columnDefinition = "DECIMAL DEFAULT 0", nullable = false)
	private BigDecimal salaryMax;

	@Column(name = "unit", columnDefinition = "VARCHAR(20)", nullable = false)
	private String unit;

	@Column(name = "company", columnDefinition = "VARCHAR(100)", nullable = false)
	private String company;

	@Column(name = "tel", columnDefinition = "VARCHAR(20)", nullable = false)
	private String tel;

	@Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
	private String email;

	@Column(name = "expired_date", columnDefinition = "VARCHAR(50)")
	private String expiredDate;

	@Column(name = "flag", columnDefinition = "BIT DEFAULT 0", nullable = false)
	private boolean flag;

	@Column(name = "poster_id", columnDefinition = "BIGINT")
	private Long posterId;
	
	@Column(name = "is_del", columnDefinition = "BIT DEFAULT 0", nullable = false)
	private boolean isDel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Float getExperience() {
		return experience;
	}

	public void setExperience(Float experience) {
		this.experience = experience;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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

	public BigDecimal getSalaryMax() {
		return salaryMax;
	}

	public void setSalaryMax(BigDecimal salaryMax) {
		this.salaryMax = salaryMax;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
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

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long getPosterId() {
		return posterId;
	}

	public void setPosterId(Long posterId) {
		this.posterId = posterId;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
