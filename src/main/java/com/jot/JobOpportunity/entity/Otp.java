package com.jot.JobOpportunity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "otp")
public class Otp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "account_id", columnDefinition = "BIGINT", nullable = false)
    private Long accountId;
	
	@Column(name = "otp", columnDefinition = "VARCHAR(20)", nullable = false)
	private String otp;
	
	@Column(name = "create_time", columnDefinition = "VARCHAR(50)", nullable = false)
    private String createTime;

	public Otp() {
	}

	public Otp(Long accountId, String otp, String createTime) {
		super();
		this.accountId = accountId;
		this.otp = otp;
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
