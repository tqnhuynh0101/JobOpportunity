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
@Table(name= "CV_send")
public class CvSend extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="employee_id", columnDefinition = "BIGINT", nullable = false, unique = true)
	private Long employeeId;
	
	@Column(name="name", columnDefinition = "VARCHAR(200)", nullable = false)
	private String name;
    
	@Column(name="email", columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;
    
	@Column(name="tel", columnDefinition = "VARCHAR(15)", nullable = false)
    private String tel;
    
	@Column(name="gender", columnDefinition = "BIT", nullable = false)
    private boolean gender;
    
	@Column(name="age", columnDefinition = "INT", nullable = false)
    private int age;
	
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

	@Column(name ="uuid", columnDefinition = "VARCHAR(50)")
	private String uuid;

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
