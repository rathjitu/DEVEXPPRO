package com.devexperts.service;


import com.devexperts.model.account.Account;
import com.devexperts.model.account.AccountKey;
import com.devexperts.model.service.AccountService;

import lombok.NonNull;

import org.springframework.stereotype.Service;

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
    public void transfer(Account source, Account target, double amount) {
        //do nothing for now
    }

}
