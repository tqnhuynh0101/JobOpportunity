package com.jot.JobOpportunity.service.imp;

import java.security.SecureRandom;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Characters;
import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.dto.account.AccountInforSendMail;
import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.AccountRepository;
import com.jot.JobOpportunity.service.MailService;
import com.jot.JobOpportunity.service.OtpService;

@Service
@Transactional
public class OtpServiceImp implements OtpService {

	private final Logger log = LoggerFactory.getLogger(OtpServiceImp.class);


	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MailService mailService;


	@Override
	public String create(Long accountId) {
		log.debug("Request to create Random Otp");
		String number = "0123456789";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			sb.append(number.charAt(rnd.nextInt(number.length())));
		}
		String strOtp = sb.toString();
		return strOtp;
	}

	@Override
	public DataResponse check(String otp, String id) {
		log.debug("Request to check Otp");
		DataResponse res = new DataResponse();
		if(otp == null) {
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.ERROR_OTP);
			return res;
		}
		try{
			Long accId = Long.parseLong(id);
			String password = Characters.getStringRamdom();
			Account account = accountRepository.getAccountById(accId);
			account.setPassword(passwordEncoder.encode(password));
			accountRepository.save(account);
			mailService.sendMailSuccessOtp(new AccountInforSendMail(account.getUsername(), password, account.getEmail(), account.getName()));
			res.setStatus(Constants.SUCCESS);
			res.setMessage(Constants.SUCCESS_OTP);
			return res;
		}catch (Exception e){
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

//	@Override
//	public void deleteById(Long id) {
//		otpRepository.deleteById(id);
//	}
//
//	@Override
//	public void deleteOtpByAccountId(Long accountId) {
//		otpRepository.deleteByAccountId(accountId);
//	}

}
