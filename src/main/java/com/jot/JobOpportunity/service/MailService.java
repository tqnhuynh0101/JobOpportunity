package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.dto.account.AccountInforSendMail;
import com.jot.JobOpportunity.dto.account.AccountOtpSendMail;

/**
 * Mail Service interface
 **/
public interface MailService {

	public void sendMailRegister(AccountInforSendMail account);

	public void sendMailOtp(AccountOtpSendMail account);

	public void sendMailSuccessOtp(AccountInforSendMail account);
}
