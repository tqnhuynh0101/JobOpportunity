package com.jot.JobOpportunity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author bao wards
 */

@Entity
@Table(name = "wards")
public class Wards extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", columnDefinition = "VARCHAR(20)", nullable = false)
	private String code;

	@Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
	private String name;

	@Column(name = "full_name", columnDefinition = "VARCHAR(255)", nullable = false)
	private String fullName;

	@Column(name = "district_code", columnDefinition = "VARCHAR(20)", nullable = false)
	private String districtCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

}
