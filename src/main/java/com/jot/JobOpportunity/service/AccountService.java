package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.dto.account.AccountChangePass;
import com.jot.JobOpportunity.dto.account.AccountRegister;
import com.jot.JobOpportunity.dto.account.AccountUpdateInforDto;
import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.entity.response.DataResponse;

public interface AccountService {

	Account getAccountByUsername(String username);

	Account getAccountById(Long id);

	DataResponse register(AccountRegister accountRegister);
	
	Account getAccountLogin();

	DataResponse getAccountCv();

	DataResponse changePass(AccountChangePass accountChangePass);

	DataResponse getAccountForgotPass(String userName);
	
	DataResponse accountUpdateInformation(AccountUpdateInforDto accountUpdateInforDtos);

	DataResponse getAccountManagement();

	DataResponse deleteAccountById(String id);
}
