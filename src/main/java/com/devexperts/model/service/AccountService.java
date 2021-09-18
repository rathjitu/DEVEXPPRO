package com.devexperts.model.service;

import com.devexperts.model.account.Account;


public interface AccountService {
	public void clear();
	public void createAccount(Account account) ;
	public Account getAccount(long id) ;
	public void transfer(Account source, Account target, double amount);
}
