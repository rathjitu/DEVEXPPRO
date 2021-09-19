package com.devexperts.rest;

import com.devexperts.exceptions.AccountNotExistException;
import com.devexperts.exceptions.InSufficientFundException;
import com.devexperts.model.account.Account;
import com.devexperts.model.account.AccountType;
import com.devexperts.model.account.ApplicationConstants;
import com.devexperts.model.account.MoneyTransaction;
import com.devexperts.model.account.ProjectStatus;
import com.devexperts.model.rest.AbstractAccountController;
import com.devexperts.model.service.AccountService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class AccountController implements AbstractAccountController {
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/operations/transfer", method = RequestMethod.POST,
	 consumes = MediaType.APPLICATION_JSON_VALUE,
     produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectStatus> transfer(@RequestBody MoneyTransaction moneyTransaction) {
		
		boolean trasferStatus=accountService.transfer( moneyTransaction);
    	 if(trasferStatus) {
    		 return new ResponseEntity<ProjectStatus>(new ProjectStatus("Successful transfer"),HttpStatus.OK); 
    	 }else {
    		 return new ResponseEntity<ProjectStatus>(new ProjectStatus("Bad Request"), HttpStatus.BAD_REQUEST);
    	 }
    }

	@PostMapping(value = "/operations/create",
	 consumes = MediaType.APPLICATION_JSON_VALUE,
     produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public  ResponseEntity<List<Account>>  createAccount(@RequestBody Account account) {
		List<Account> response = accountService.createAccount(account);
	        if(response==null) {
	        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }
	       return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	
	@ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return new ResponseEntity("One of the parameters in not present or amount is invalid", HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = InSufficientFundException.class)
    public ResponseEntity handleIllegalArgumentException(InSufficientFundException illegalArgumentException) {
        return new ResponseEntity(" Insufficient account balance", HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@ExceptionHandler(value = AccountNotExistException.class)
    public ResponseEntity handleAccountNotExistException(AccountNotExistException accountNotExistException) {
        return new ResponseEntity(" Account not found", HttpStatus.NOT_FOUND);
    }
}
