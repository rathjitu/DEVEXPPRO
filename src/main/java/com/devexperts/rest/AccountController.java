package com.devexperts.rest;

import com.devexperts.exceptions.InSufficientFundException;
import com.devexperts.model.account.Account;
import com.devexperts.model.account.MoneyTransaction;
import com.devexperts.model.account.ProjectStatus;
import com.devexperts.model.rest.AbstractAccountController;
import com.devexperts.model.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class AccountController implements AbstractAccountController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;

	@PostMapping(value = "/operations/transfer",
	 consumes = MediaType.APPLICATION_JSON_VALUE,
     produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectStatus> transfer(@RequestBody MoneyTransaction moneyTransaction) {
		
		Account response =accountService.transfer( moneyTransaction);
    	 if(response!=null) {
    		 return new ResponseEntity<ProjectStatus>(new ProjectStatus("Successful transfer"),HttpStatus.OK); 
    	 }else {
    		 return new ResponseEntity<ProjectStatus>(new ProjectStatus("Bad Request"), HttpStatus.BAD_REQUEST);
    	 }
    }


	@PostMapping(value = "/operations/create",
	 consumes = MediaType.APPLICATION_JSON_VALUE,
	 produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseBody
	public ResponseEntity<List<Account>> createAccount(@RequestBody Account account) {
		accountService.createAccount(account);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/accounts/{id}")
	public ResponseEntity<Object> getAccounts(@PathVariable Long id) {
		Account response =null;
		try {
			response = accountService.getAccount(id);
			if (response==null) {
				return new ResponseEntity<>("No Account exist",HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ProjectStatus("Bad Request"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/accounts")
	public ResponseEntity getAccounts() {
		try {
			List<Account> response =null;
			response = accountService.getAccounts();
			if (response==null || response.isEmpty()) {
				return new ResponseEntity<>("No Account exist",HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return new ResponseEntity<String>("One of the parameters in not present or amount is invalid", HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = InSufficientFundException.class)
    public ResponseEntity<String> handleIllegalArgumentException(InSufficientFundException illegalArgumentException) {
        return new ResponseEntity<String>(" Insufficient account balance", HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> handleAccountNotExistException(EntityNotFoundException accountNotExistException) {
        return new ResponseEntity<String>(" Account not found", HttpStatus.NOT_FOUND);
    }
}
