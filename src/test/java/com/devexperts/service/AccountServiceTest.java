package com.devexperts.service;

/**
 * 
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;

import com.devexperts.exceptions.InSufficientFundException;
import com.devexperts.model.account.Account;
import com.devexperts.model.account.AccountKey;
import com.devexperts.model.service.AccountService;
import com.devexperts.service.AccountServiceImpl;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentMatchers;
/**
 * @author jitrath
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	   @InjectMocks
	   private AccountService accountService = new AccountServiceImpl();
		@Mock
		private AccountService accountServiceMock;

	   @Test
	   public void gettingAccount() {
		  AccountKey accountKey= AccountKey.getRandom();
	      accountService.createAccount(new Account(accountKey,new BigDecimal(0.0)));
	      assertEquals(accountService.getAccount(accountKey.getValue()).getAccountKey().getValue(),accountKey.getValue());
	   }
	   
	   @Test
	   public void createAccount() {
		   AccountKey accountKey= AccountKey.getRandom();
		  doNothing().when(accountServiceMock).createAccount(ArgumentMatchers.any(Account.class));
		  accountServiceMock.createAccount(new Account(accountKey,new BigDecimal(0.0)));
	   // Assert
	      Mockito.verify(accountServiceMock, Mockito.times(1)).createAccount(ArgumentMatchers.any(Account.class));
	   }
	   
	   @Test
	   public void trasferInSufficientFund() {
		  Account accountSource = new Account(AccountKey.getRandom(),new BigDecimal(10.0));
		  Account accountTarget = new Account(AccountKey.getRandom(),new BigDecimal(20.0));
		  accountService.createAccount(accountSource);
		  accountService.createAccount(accountTarget);
		  Mockito.lenient().when(accountServiceMock.transfer(accountSource, accountTarget, new BigDecimal(12.0)))
		  .thenThrow(InSufficientFundException.class);
		  //accountService.transfer(accountSource, accountTarget, new BigDecimal(12.0));
	   }

	}
