package com.devexperts.service;


import com.devexperts.exceptions.AccountNotExistException;
import com.devexperts.exceptions.InSufficientFundException;
import com.devexperts.model.account.Account;
import com.devexperts.model.account.ApplicationConstants;
import com.devexperts.model.account.MoneyTransaction;
import com.devexperts.model.service.AccountService;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class AccountServiceImpl implements AccountService {

	private List<Account> accounts;

	public AccountServiceImpl() {
		this.accounts = new ArrayList<>();
		this.accountLock = new ReentrantReadWriteLock();
	}

	private ReadWriteLock accountLock;

	@Override
	public void clear() {
		accounts = new ArrayList<>();
	}

	@Override
	// Null Check added lombok jar
	public List<Account> createAccount(@NonNull Account account) {
		this.accountLock.writeLock().lock();
		try {
			accounts.add(account);
		} finally {
			this.accountLock.writeLock().unlock();
		}
		return accounts;
	}

	@Override
	public Account getAccount(long id) {
		this.accountLock.readLock().lock();
		// return accounts.stream()
		try {
			return accounts.stream().parallel().filter(account -> account.getAccountId() == id).findAny().orElse(null);
		} finally {
			this.accountLock.readLock().unlock();
		}
	}


	@Override
	public Boolean transfer(MoneyTransaction moneyTransaction) {
		if(moneyTransaction.getAccountFromId()==null||moneyTransaction.getAccountToId()==null||moneyTransaction.getAmount()==null){
			throw new IllegalArgumentException(ApplicationConstants.SOURCE_ACCOUNT_NOTEXIST);
		}
		Account source = this.getAccount(moneyTransaction.getAccountFromId());
		Account target = this.getAccount(moneyTransaction.getAccountToId());
		BigDecimal amount = moneyTransaction.getAmount();
		if (moneyTransaction.getAmount().compareTo(BigDecimal.valueOf(0.0)) == 0) {
			throw new InSufficientFundException(ApplicationConstants.NOT_A_VALID_AMOUNT);
		}
		if (source == null) {
			throw new AccountNotExistException(ApplicationConstants.SOURCE_ACCOUNT_NOTEXIST);
		}
		if (target == null) {
			throw new AccountNotExistException(ApplicationConstants.DESTINATION_ACCOUNT_NOTEXIST);
		}
		this.accountLock.writeLock().lock();
		try {

			if ((source.getBalance().compareTo(BigDecimal.valueOf(0.0)) == 0)
					|| (source.getBalance().compareTo(amount) < 0)) {
				throw new InSufficientFundException("Current balance  is less than requested amount");
			}
			boolean success = source.withdraw(amount);
			if (success) {
				target.deposit(amount);
				return true;
			}
			return false;
		} finally {
			this.accountLock.writeLock().unlock();
		}
	}
	

}
