package com.jot.JobOpportunity.service.imp;

import java.security.SecureRandom;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Characters;
import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.account.AccountInforSendMail;
import com.jot.JobOpportunity.dto.otp.OtpCheck;
import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.entity.Otp;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.AccountRepository;
import com.jot.JobOpportunity.repository.OtpRepository;
import com.jot.JobOpportunity.service.MailService;
import com.jot.JobOpportunity.service.OtpService;

@Service
@Transactional
public class OtpServiceImp implements OtpService {

	private final Logger log = LoggerFactory.getLogger(OtpServiceImp.class);

	@Autowired
	OtpRepository otpRepository;

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
		Otp otp = new Otp(accountId, strOtp, Utils.currentDateTime());
		otpRepository.save(otp);
		return strOtp;
	}

	@Override
	public DataResponse check(OtpCheck otpCheck) {
		log.debug("Request to check Otp");
		DataResponse res = new DataResponse();
		if(otpCheck == null) {
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.ERROR_OTP);
			return res;
		}
		try{
			Otp otpDb = otpRepository.getOtpByAccountIdAndOtp(otpCheck.getAccountId(), otpCheck.getOtp());
			if (otpDb != null) {
				Date current = Utils.getCurrentDateTime();
				Date createTime = Utils.getDateTime(otpDb.getCreateTime());
				// 180000 : 3 phút
				if ((current.getTime() - createTime.getTime()) <= 180000) {
					String password = Characters.getStringRamdom();
					Account account = accountRepository.getAccountById(otpCheck.getAccountId());
					account.setPassword(passwordEncoder.encode(password));
					accountRepository.save(account);
					mailService.sendMailSuccessOtp(
							new AccountInforSendMail(account.getUsername(), password, account.getEmail(), account.getName()));
					res.setStatus(Constants.SUCCESS);
					res.setMessage(Constants.SUCCESS_OTP);
					return res;
				}
			}
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.ERROR_OTP);
			return res;
		}catch (Exception e){
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

	@Override
	public void deleteById(Long id) {
		otpRepository.deleteById(id);
	}

	@Override
	public void deleteOtpByAccountId(Long accountId) {
		otpRepository.deleteByAccountId(accountId);
	}

}
