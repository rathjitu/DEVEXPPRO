package com.devexperts.service;


import com.devexperts.dao.AccountDao;
import com.devexperts.exceptions.InSufficientFundException;
import com.devexperts.model.account.Account;
import com.devexperts.model.account.ApplicationConstants;
import com.devexperts.model.account.MoneyTransaction;
import com.devexperts.model.service.AccountService;

import lombok.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.persistence.EntityNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	@Autowired
	private AccountDao accountDao;

	public AccountServiceImpl() {
		this.accountLock = new ReentrantReadWriteLock();
	}

	private ReadWriteLock accountLock;

	@Override
	public void clear() {
		// accounts = new ArrayList<>();
	}

	@Override
	// Null Check added lombok jar
	public void createAccount(@NonNull Account account) {
		this.accountLock.writeLock().lock();
		try {
			accountDao.save(account);
		} finally {
			this.accountLock.writeLock().unlock();
		}
	}

	@Override
	public Account getAccount(long id) {
		Account account = null;
		this.accountLock.readLock().lock();
		// accountDao.get
		try {
			account = accountDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
		} finally {
			this.accountLock.readLock().unlock();
		}
		return account;
	}

	@Override
	public List<Account> getAccounts() {
		try {
			this.accountLock.readLock().lock();
			List<Account> accountRecords = new ArrayList<>();
			accountDao.findAll().forEach(accountRecords::add);
			return accountRecords;
		} finally {
			this.accountLock.readLock().unlock();
		}
	}

	@Override
	public Account transfer(MoneyTransaction moneyTransaction) {
		if (moneyTransaction.getAccountFromId() == null || moneyTransaction.getAccountToId() == null
				|| moneyTransaction.getAmount() == null) {
			throw new IllegalArgumentException(ApplicationConstants.SOURCE_ACCOUNT_NOTEXIST);
		}
		if (moneyTransaction.getAmount().compareTo(BigDecimal.valueOf(0.0)) == 0) {
			throw new IllegalArgumentException(ApplicationConstants.NOT_A_VALID_AMOUNT);
		}
		Account source = this.getAccount(moneyTransaction.getAccountFromId());
		Account target = this.getAccount(moneyTransaction.getAccountToId());
		BigDecimal amount = moneyTransaction.getAmount();
		if (source == null) {
			throw new EntityNotFoundException(ApplicationConstants.SOURCE_ACCOUNT_NOTEXIST);
		}
		if (target == null) {
			throw new EntityNotFoundException(ApplicationConstants.DESTINATION_ACCOUNT_NOTEXIST);
		}
		this.accountLock.writeLock().lock();
		try {

			if ((source.getBalance().compareTo(BigDecimal.valueOf(0.0)) == 0)
					|| (source.getBalance().compareTo(amount) < 0)) {
				throw new InSufficientFundException("Current balance  is less than requested amount");
			}
			Account accountSrc=this.withdraw(source,amount);
			if (accountSrc!=null) {
				Account accountTarget=this.deposit(target,amount);
				if(accountTarget!=null) {
				accountDao.save(source);
				accountDao.save(target);
				}
			}
		} finally {
			this.accountLock.writeLock().unlock();
		}
		return target;
	}

	private Account withdraw(Account source ,BigDecimal withdrawAmount) {
		if (withdrawAmount.compareTo(source.getBalance()) < 1) {
			source.setBalance(source.getBalance().subtract(withdrawAmount));
			return source;
		}
		return null;	
	}

	public Account deposit(Account target, BigDecimal depositAmount) {
		if (depositAmount.compareTo(BigDecimal.valueOf(0.0)) == 1) { // if the depositAmount is valid
			target.setBalance(target.getBalance().add(depositAmount));
			return target;
		} else {
			return null;
		}
	}

}
