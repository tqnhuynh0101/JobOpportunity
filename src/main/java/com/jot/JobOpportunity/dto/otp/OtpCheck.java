package com.jot.JobOpportunity.dto.otp;

public class OtpCheck {
	
	private String otp;
	
	private Long accountId;

	public OtpCheck() {
	}

	public OtpCheck(String otp, Long accountId) {
		super();
		this.otp = otp;
		this.accountId = accountId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
