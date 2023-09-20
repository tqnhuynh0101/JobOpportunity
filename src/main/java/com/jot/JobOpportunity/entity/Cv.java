package com.jot.JobOpportunity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ACER
 * CV 
 */
@Entity
@Table(name= "CV")
public class Cv extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="employee_id", columnDefinition = "BIGINT", nullable = false)
	private Long employeeId;
	
	@Column(name="intro", columnDefinition = "VARCHAR(500)", nullable = false)
	private String intro;
	
	@Column(name="pos", columnDefinition = "VARCHAR(100)", nullable = false)
	private String pos;
	
	@Column(name="address", columnDefinition = "VARCHAR(200)", nullable = false)
	private String address;
	
	@Column(name="education", columnDefinition = "TEXT", nullable = false)
	private String education;
	
	@Column(name="work_experience", columnDefinition = "TEXT", nullable = false)
	private String workExperience;
	
	@Column(name="certifications", columnDefinition = "TEXT", nullable = false)
	private String certifications;
	
	@Column(name="awards", columnDefinition = "TEXT", nullable = false)
	private String awards;
	
	@Column(name="interest", columnDefinition = "TEXT", nullable = false)
	private String interest;
	
	@Column(name="avatar", columnDefinition = "VARCHAR(200)", nullable = true)
	private String avatar;

	@Column(name ="uuid", columnDefinition = "VARCHAR(200)")
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
