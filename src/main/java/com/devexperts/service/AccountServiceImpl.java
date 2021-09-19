package com.devexperts.service;


import com.devexperts.exceptions.InSufficientFundException;
import com.devexperts.model.account.Account;
import com.devexperts.model.account.AccountKey;
import com.devexperts.model.service.AccountService;

import lombok.NonNull;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class AccountServiceImpl implements AccountService {
	/*
	 * We cannot use final if we have change the value private final List<Account>
	 * accounts = new ArrayList<>();
	 */
	private List<Account> accounts;
	private final  static String SOURCE_ACCOUNT_NOTEXIST = "Source account number is not exist";
    private final static String DESTINATION_ACCOUNT_NOTEXIST = "Destination account number is not exist";
    private final static String NOT_A_VALID_AMOUNT = "Not a Valid Amount";

	public AccountServiceImpl() {
		this.accounts = new ArrayList<>();
		this.accountLock = new ReentrantReadWriteLock();
	}

	private ReadWriteLock accountLock;

	@Override
	public void clear() {
		/*
		 * Clear is faster when list is very short The new ArrayList will start with a
		 * small array, and Calling clear leaves the array at the size it was
		 * immediately before.So advised to use new ArrayList accounts.clear();
		 */
		accounts = new ArrayList<>();
	}

	@Override
	// Null Check added lombok jar
	public void createAccount(@NonNull Account account) {
		this.accountLock.writeLock().lock();
		try {
			accounts.add(account);
		} finally {
			this.accountLock.writeLock().unlock();
		}
	}

	@Override
	public Account getAccount(long id) {
		this.accountLock.readLock().lock();
		// return accounts.stream()
		try {
			return accounts.stream().parallel().filter(account -> account.getAccountKey() == AccountKey.getType(id))
					.findAny().orElse(null);
		} finally {
			this.accountLock.readLock().unlock();
		}
	}

	@Override
	public boolean transfer(Account source, Account target, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.valueOf(0.0)) == 0) {
			throw new IllegalArgumentException(NOT_A_VALID_AMOUNT);
		}
		this.accountLock.writeLock().lock();
		try {
			if (getAccount(source.getAccountKey().getValue()) != null) {
				if ((source.getBalance().compareTo(BigDecimal.valueOf(0.0)) == 0)
						|| (source.getBalance().compareTo(amount) < 0)) {
					throw new InSufficientFundException("Current balance  is less than requested amount");
				}
				boolean success = source.withdraw(amount);
				if (success) {
					if (getAccount(source.getAccountKey().getValue()) != null) {
						target.deposit(amount);
						return true;
					} else {
						throw new IllegalArgumentException(DESTINATION_ACCOUNT_NOTEXIST);
					}
				}
			} else {
				throw new IllegalArgumentException(SOURCE_ACCOUNT_NOTEXIST);
			}
			return false;
		} finally {
			this.accountLock.writeLock().unlock();
		}
	}
	
	
	public static void main (String args[]) {
    	Account accountSource = new Account(AccountKey.getRandom(),BigDecimal.valueOf(13.0));
		Account accountTarget = new Account(AccountKey.getRandom(),BigDecimal.valueOf(0.0));
		
		AccountService accountService= new AccountServiceImpl();
		accountService.createAccount(accountSource);
		accountService.createAccount(accountTarget);
		//Mockito.lenient().when(accountServiceMock.transfer(accountSource, accountTarget, new BigDecimal(12.0)))
    	System.out.println(accountService.transfer(accountSource, accountTarget,BigDecimal.valueOf(0.0))+"...accountSource"+
		accountSource.getBalance()+"..accountTarget"+accountTarget.getBalance());
    }

}
