/**
 * 
 */
package com.devexperts.model.account;

/**
 * @author jitrath
 *
 */
public class Account {

	private AccountKey accountKey;
	

	private double balance;
	 
	// Account constructor that receives two parameters
	 public Account(AccountKey accountKey, double balance)
	{
		this.accountKey = accountKey; 

		if (balance > 0.0) // if the balance is valid
			this.balance = balance; // assign it to instance variable balance
	}
 
    public void withdraw(double withdrawAmount) {
    	if (withdrawAmount < balance) { 
    		this.balance -= withdrawAmount;
    	}
    }
 
    public void deposit(double depositAmount) {
    	if (depositAmount > 0.0) { // if the depositAmount is valid     
    		balance = balance + depositAmount; 
    	}
    }
 
    public double getBalance() {
        return this.balance;
    }

	public AccountKey getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(AccountKey accountKey) {
		this.accountKey = accountKey;
	}

}
