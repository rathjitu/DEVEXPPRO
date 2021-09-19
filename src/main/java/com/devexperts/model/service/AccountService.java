package com.devexperts.model.service;

import java.math.BigDecimal;
import java.util.List;

import com.devexperts.model.account.Account;
import com.devexperts.model.account.MoneyTransaction;


public interface AccountService {
	public void clear();
	public List<Account> createAccount(Account account) ;
	public Account getAccount(long id) ;
	Boolean transfer(MoneyTransaction moneyTransaction);
}

