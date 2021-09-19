package com.devexperts.model.service;

import java.math.BigDecimal;

import com.devexperts.model.account.Account;


public interface AccountService {
	public void clear();
	public void createAccount(Account account) ;
	public Account getAccount(long id) ;
	public boolean transfer(Account source, Account target, BigDecimal amount);
}

