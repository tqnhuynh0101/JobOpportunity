package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.dto.account.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Characters;
import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.common.Validate;
import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.entity.ApprovedPost;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.AccountRepository;
import com.jot.JobOpportunity.security.Authorities;
import com.jot.JobOpportunity.service.AccountService;
import com.jot.JobOpportunity.service.ApprovedPostService;
import com.jot.JobOpportunity.service.MailService;
import com.jot.JobOpportunity.service.OtpService;

import java.util.List;

@Service
@Transactional
public class AccountServiceImp implements AccountService {

	private final Logger log = LoggerFactory.getLogger(AccountServiceImp.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ApprovedPostService approvedPostService;

	@Autowired
	private OtpService otpService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private MailService mailService;

	@Override
	public Account getAccountByUsername(String username) {
		return accountRepository.getAccountByUsername(username);
	}

	@Override
	public Account getAccountById(Long id) {
		return accountRepository.getAccountById(id);
	}


	@Transactional
	@Override
	public DataResponse register(AccountRegister accountRegister) {
		DataResponse res = new DataResponse();
		try {
			Account account = mapper.map(accountRegister, Account.class);

			// check age 18 - 59
			if (account.getAge() < 18 || account.getAge() >= 60) {
				res.setMessage(Constants.REGISTER_FAIL);
				res.setStatus(Constants.ERROR);
				return res;
			}
			// check email
			if (!Validate.validateEmail(account.getEmail())) {
				res.setMessage(Constants.REGISTER_FAIL);
				res.setStatus(Constants.ERROR);
				return res;
			}

			// check tel
			if (!Validate.validateTel(account.getTel())) {
				res.setMessage(Constants.REGISTER_FAIL);
				res.setStatus(Constants.ERROR);
				return res;
			}
			// check authority
			if (!(Constants.EMPLOYEE.equals(account.getAuthority())
					|| Constants.SEEKER.equals(account.getAuthority()))) {
				res.setMessage(Constants.REGISTER_FAIL);
				res.setStatus(Constants.ERROR);
				return res;
			}
			account.setAuthority(Constants.ROLE + account.getAuthority());

			String password = Characters.getStringRamdom();
			account.setPassword(passwordEncoder.encode(password));

			account.setName(Characters.conver(account.getName()));

			String username = Characters.abbreviation(account.getName());
			Long count = accountRepository.countByUsername(username + "[1-9]", username);
			if (count > 0) {
				username = username + "" + (count);
			}
			account.setUsername(username);

			account.setCreateBy(Constants.GUEST);
			account.setCreateTime(Utils.currentDateTime());

			accountRepository.save(account);

			if (account.getAuthority().equals(Authorities.SEEKER)) {
				ApprovedPost ap = new ApprovedPost(account.getId(), true, 0, null);
				ap.setCreateBy(Constants.GUEST);
				ap.setCreateTime(Utils.currentDateTime());
				approvedPostService.save(ap);
			}

			mailService.sendMailRegister(
					new AccountInforSendMail(username, password, account.getEmail(), account.getName()));

			res.setStatus(Constants.SUCCESS);
			res.setMessage(Constants.REGISTER_SUCCESS);
			return res;
		} catch (Exception e) {
			res.setMessage(Constants.REGISTER_FAIL);
			res.setStatus(Constants.ERROR);
			return res;
		}
	}

	@Override
	public Account getAccountLogin() {
		return this.getAccountByUsername(Utils.getCurrentUserLogin().get());
	}

	@Override
	public DataResponse getAccountCv() {
		log.debug("Request change get account cv");
		DataResponse res = new DataResponse();
		try {
			Account account = accountRepository.getAccountByUsername(Utils.getCurrentUserLogin().get());
			AccountCV accountCV = mapper.map(account, AccountCV.class);
			res.setResult(accountCV);
			res.setStatus(Constants.SUCCESS);
			return res;
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			return res;
		}
	}

	@Override
	public DataResponse getAccountForgotPass(String userName) {
		DataResponse res = new DataResponse();
		try {
			Account account = this.getAccountByUsername(userName);
			if (account == null) {
				res.setStatus(Constants.NOT_FOUND);
				res.setMessage(Constants.ACCOUNT_NOT_FOUND);
				return res;
			} else {
				String otp = otpService.create(account.getId());
				mailService.sendMailOtp(new AccountOtpSendMail(account.getEmail(), account.getName(), otp));
				res.setStatus(Constants.SUCCESS);
				res.setMessage(Constants.SEND_OTP_MAIL);
				res.setResult(account.getId());
				return res;
			}
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

	@Override
	public DataResponse changePass(AccountChangePass accountChangePass) {
		log.debug("Request change password");
		DataResponse res = new DataResponse();
		try {
			Account account = this.getAccountLogin();
			if (accountChangePass.getNewPassword().length() < 5) {
				res.setStatus(Constants.ERROR);
				res.setMessage(Constants.NEW_PASSWORD_ERROR);
				return res;
			}
			if (!passwordEncoder.matches(accountChangePass.getOldPassword(), account.getPassword())) {
				res.setStatus(Constants.ERROR);
				res.setMessage(Constants.OLD_PASSWORD_ERROR);
				return res;
			}
			account.setCreateBy(Constants.GUEST);
			account.setCreateTime(Utils.currentDateTime());
			account.setPassword(passwordEncoder.encode(accountChangePass.getNewPassword()));
			accountRepository.save(account);
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			return res;
		}
		res.setStatus(Constants.SUCCESS);
		res.setMessage(Constants.CHANGE_PASSWORD_SUCCESS);
		return res;
	}

	@Override
	public DataResponse accountUpdateInformation(AccountUpdateInforDto accountUpdateInforDtos) {
		log.debug("Request update information");
		DataResponse res = new DataResponse();
		try {
			Account account = this.getAccountLogin();
			if (accountUpdateInforDtos.getAge() < 18 || accountUpdateInforDtos.getAge() >= 60) {
				res.setMessage(Constants.UPDATE_FAIL);
				res.setStatus(Constants.ERROR);
				return res;
			}
			if (!Validate.validateEmail(accountUpdateInforDtos.getEmail())) {
				res.setMessage(Constants.UPDATE_FAIL);
				res.setStatus(Constants.ERROR);
				return res;
			}

			if (!Validate.validateTel(accountUpdateInforDtos.getTel())) {
				res.setMessage(Constants.UPDATE_FAIL);
				res.setStatus(Constants.ERROR);
				return res;
			}
			accountRepository.updateInformation(accountUpdateInforDtos.getAge(),
					accountUpdateInforDtos.getTel(),
					accountUpdateInforDtos.getEmail(), 
					accountUpdateInforDtos.getGender(),
					account.getId());
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.UPDATE_FAIL);
			return res;
		}
		res.setStatus(Constants.SUCCESS);
		res.setMessage(Constants.UPDATE_SUCCESS);
		return res;
	}

	@Override
	public DataResponse getAccountManagement() {
		log.debug("Request get account management");
		DataResponse res = new DataResponse();
		try {
			Account account = this.getAccountLogin();
			if(!account.getAuthority().equals(Constants.ROLE_ADMIN)) {
				res.setStatus(Constants.ERROR);
				res.setMessage(Constants.ACCOUNT_NOT_AUTHORITY);
				return res;
			}
			List<Account> listDb = accountRepository.getAccountManagement();
			if (listDb == null) {
				res.setStatus(Constants.NOT_FOUND);
				res.setMessage(Constants.DATA_EMPTY);
				return res;
			} else {
				List<AccountManagementDto> list = Utils.mapList(listDb, AccountManagementDto.class);
				res.setStatus(Constants.SUCCESS);
				res.setResult(list);
				return res;
			}
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.DATA_ERROR);
			return res;
		}
	}

	@Override
	public DataResponse deleteAccountById(String id) {
		log.debug("AccountServiceImp.deleteAccountById()");
		DataResponse res = new DataResponse();
		try {
			Long accountId = Long.parseLong(id);
			accountRepository.deleteAccountById(accountId);
			res.setStatus(Constants.SUCCESS);
			res.setMessage(Constants.DELETE_SUCCESS);
			return res;
		} catch (Exception e) {
			log.debug("Error deleteAccountById in AccountServiceImp");
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.DATA_ERROR);
			return res;
		}
	}
}
