package com.jot.JobOpportunity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.dto.account.AccountChangePass;
import com.jot.JobOpportunity.dto.account.AccountLogin;
import com.jot.JobOpportunity.dto.account.AccountRegister;
import com.jot.JobOpportunity.dto.account.AccountUpdateInforDto;
import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.entity.response.LoginResponse;
import com.jot.JobOpportunity.security.CustomUserDetails;
import com.jot.JobOpportunity.security.JwtTokenProvider;
import com.jot.JobOpportunity.service.AccountService;
import com.jot.JobOpportunity.service.OtpService;

/**
 * Account controller.
 **/
@RestController
@RequestMapping("/api/account")
public class AccountController {
		private final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	OtpService otpService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public DataResponse login(@RequestBody AccountLogin accountLogin) throws Exception {
		log.debug("Request login");
		DataResponse res = new DataResponse();
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(accountLogin.getUsername(), accountLogin.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
			res.setStatus(Constants.SUCCESS);
			res.setMessage(Constants.LOGIN_SUCCESS);
			Account account = accountService.getAccountLogin();
			res.setResult(new LoginResponse(jwt, account.getUsername(), account.getName(), account.getAuthority()));
			return res;
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.LOGIN_FAIL);
			return res;
		}
		
	}
	
	@PostMapping("/register")
	public DataResponse register(@RequestBody AccountRegister accountRegister) throws Exception {
		log.debug("Request register");
		DataResponse res = accountService.register(accountRegister);
		return res;
	}
	
	@PostMapping("/change-password")
	public DataResponse changePass(@RequestBody AccountChangePass accountChangePass) {
		log.debug("Request change password");
		DataResponse res = accountService.changePass(accountChangePass);
		return res;
	}

	@GetMapping("/forgot-password/{username}")
	public DataResponse forgotPass(@PathVariable("username") String userName) {
		log.debug("Request forgot password");
		DataResponse res = accountService.getAccountForgotPass(userName);
		return res;
	}
	
	@PutMapping("/check-otp")
    public DataResponse checkOtp(@RequestParam("otp") String otp,@RequestParam("id") String id) {
        log.debug("REST request to check otp : {}", id);
        DataResponse res = otpService.check(otp, id);
		return res;
    }

    @GetMapping("/get-account-login")
	public DataResponse getAccountCv() {
		log.debug("REST request to get account login");
		DataResponse res = accountService.getAccountCv();
		return res;
	}
    
    @PutMapping("/update-information")
    public DataResponse updateAccountInformation(@RequestBody AccountUpdateInforDto updatenformations) {
    	log.debug("Request update information");
    	DataResponse res = accountService.accountUpdateInformation(updatenformations);
    	return res;
    }

	@GetMapping("/management")
	public DataResponse getAccountManagement() {
		log.debug("REST request to get account management");
		DataResponse res = accountService.getAccountManagement();
		return res;
	}

	@DeleteMapping("/delete/{id}")
	public DataResponse deleteAccountById(@PathVariable("id") String id) {
		log.debug("AccountController.deleteAccountById()");
		DataResponse res = accountService.deleteAccountById(id);
		return res;
	}
    
}
