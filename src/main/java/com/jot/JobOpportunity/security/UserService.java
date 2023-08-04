package com.jot.JobOpportunity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.service.AccountService;

@Service
public class UserService implements UserDetailsService {

	@Lazy
	@Autowired
	private AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountService.getAccountByUsername(username);
		if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(account);
	}
	
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		Account account = accountService.getAccountById(id);
		if (account == null) {
            throw new UsernameNotFoundException(String.valueOf(id));
        }
        return new CustomUserDetails(account);
	}
}
