/**
 * 
 */
package com.devexperts.model.account;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


/**
 * @author jitrath
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"accountId", "accountType","balance"})
@JsonDeserialize(contentAs= Account.class)
public class Account {
	private Long accountId;
	private AccountType accountType;
	private BigDecimal balance;
	
	public Account() {
		
	}
	
	public Account(Long accountId, AccountType accountType, BigDecimal balance) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
    }

	public Long getAccountId() {
		return accountId;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public AccountType getAccountType() {
		return accountType;
	}


	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Account))
            return false;
        Account other = (Account)o;
        boolean accountCodeEquals = (this.accountId == null && other.accountType == null)
          || (this.balance != null && this.accountId.equals(other.accountId)&& this.balance.equals(other.balance));
        return this.accountId == other.accountId && this.balance == other.balance;
    }


}
