/**
 * 
 */
package com.devexperts.model.account;

import java.math.BigDecimal;

/**
 * @author jitrath
 *
 */
public class Account {

	private AccountKey accountKey;
	

	private BigDecimal balance;
	 
	// Account constructor that receives two parameters
	 public Account(AccountKey accountKey, BigDecimal balance)
	{
		this.accountKey = accountKey; 

		if (balance.compareTo(BigDecimal.valueOf(0.0))<1) {// if the balance is valid
			this.balance = BigDecimal.valueOf(0.0); 
		}else{
			this.balance=balance;
		}
	}
 
    public boolean withdraw(BigDecimal withdrawAmount) {
    	if (withdrawAmount.compareTo(balance)<1) { 
    		balance = balance.subtract(withdrawAmount); 
    		return true;
    	}else {
    		return false;
    	}
    }
 
    public boolean deposit(BigDecimal depositAmount) {
    	if (depositAmount.compareTo(BigDecimal.valueOf(0.0))==1) { // if the depositAmount is valid     
    		balance = balance.add(depositAmount); 
    		return true;
    	}else {
    		return false;
    	}
    }
 
    public BigDecimal getBalance() {
        return this.balance;
    }

	public AccountKey getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(AccountKey accountKey) {
		this.accountKey = accountKey;
	}

}
