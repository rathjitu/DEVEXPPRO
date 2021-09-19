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

@Service
public class AccountServiceImpl implements AccountService {
	/* We cannot use final if we have change the value 
    private final List<Account> accounts = new ArrayList<>();*/
	private  List<Account> accounts = new ArrayList<>();



	@Override
    public void clear() {
    	/*Clear is faster when list is very short 
    	The new ArrayList will start with a small array, and Calling
    	clear leaves the array at the size it was immediately before.So advised to use new ArrayList 
         accounts.clear();*/
    	accounts= new ArrayList<>();
    }

    @Override
    //Null Check added lombok jar
    public void createAccount(@NonNull Account account) {
    	accounts.add(account);
    }

    @Override
    public Account getAccount(long id) {
        //return accounts.stream()
	    	return accounts.stream().parallel()
	    	           .filter(account -> account.getAccountKey() == AccountKey.getType(id))
    	                .findAny()
    	              .orElse(null);
    }

    @Override
	public boolean transfer(Account source, Account target, BigDecimal amount) {
		if ((source.getBalance().compareTo(BigDecimal.valueOf(0.0))==0)||(source.getBalance().compareTo(source.getBalance())>1)) {
			throw new InSufficientFundException("Current balance  is less than requested amount");
		}
		boolean success=source.withdraw(amount);
		if(success) {
			target.deposit(amount);
			return true;
		}
		return false;
	}
    


}
