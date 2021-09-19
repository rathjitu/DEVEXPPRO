package com.devexperts.model.rest;

import java.util.List;
import org.springframework.http.ResponseEntity;

import com.devexperts.model.account.Account;
import com.devexperts.model.account.MoneyTransaction;
import com.devexperts.model.account.ProjectStatus;

public interface AbstractAccountController {
	 public ResponseEntity<ProjectStatus> transfer(MoneyTransaction moneyTransaction) ;
	 public  ResponseEntity<List<Account>>  createAccount(Account account);
}
