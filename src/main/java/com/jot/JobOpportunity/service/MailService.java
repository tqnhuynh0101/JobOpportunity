package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.dto.account.AccountInforSendMail;
import com.jot.JobOpportunity.dto.account.AccountOtpSendMail;
import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunDto;
import com.jot.JobOpportunity.dto.post.PostAutoSearchDto;

import java.util.List;

/**
 * Mail Service interface
 **/
public interface MailService {

	public void sendMailRegister(AccountInforSendMail account);

	public void sendMailOtp(AccountOtpSendMail account);

	public void sendMailSuccessOtp(AccountInforSendMail account);

	public void sendMailAutoSearch(List<PostAutoSearchDto> posts, AutoSearchRunDto autoSearchRunDto);
}
