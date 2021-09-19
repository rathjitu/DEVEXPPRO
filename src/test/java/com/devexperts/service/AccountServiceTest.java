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
import com.devexperts.model.account.AccountType;
import com.devexperts.model.account.MoneyTransaction;
import com.devexperts.model.service.AccountService;
import com.devexperts.service.AccountServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert; 
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
		   long generatedLongSrc = new Random().nextLong();
		   Account account=new Account(generatedLongSrc,AccountType.CREDIT,new BigDecimal(0.0));
	      List<Account> accountList=accountService.createAccount(new Account(generatedLongSrc,AccountType.CREDIT,new BigDecimal(0.0)));
	      for (Account temp : accountList) {
	            if(temp.getAccountId()==generatedLongSrc) {
	            	Assert.assertSame(temp, is(account));
	            	break;
	            }
	        }
	      
	   }
	
	   
	   @Test
	   public void trasferInSufficientFund() {
		   long generatedLongSrc = new Random().nextLong();
		  Account accountSource = new Account(generatedLongSrc,AccountType.CREDIT,new BigDecimal(10.0));
		  long generatedLongTarget= new Random().nextLong();
		  Account accountTarget = new Account(generatedLongTarget,AccountType.CREDIT,new BigDecimal(20.0));
		  accountService.createAccount(accountSource);
		  accountService.createAccount(accountTarget);
		  MoneyTransaction moneyTransaction=new MoneyTransaction(accountSource.getAccountId(),accountTarget.getAccountId(),new BigDecimal(12.0));
		  Mockito.lenient().when(accountServiceMock.transfer(moneyTransaction)).thenThrow(InSufficientFundException.class);
	   }

	}
