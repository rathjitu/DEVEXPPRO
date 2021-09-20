/**
 * 
 */
package com.devexperts.model.account;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@JsonDeserialize(contentAs= Account.class)
@Entity(name="ACCOUNT")
@Table
public class Account implements Serializable{
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	@Transient
	private AccountType accountType;
	@Column(name="BALANCE")
	private BigDecimal balance;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="LAST_NAME")
	private String lastName;

	public Account() {

	}

	public Account(Long accountId, AccountType accountType, BigDecimal balance,String firstName,String lastName) {
		this.accountId = accountId;
		this.accountType = accountType;
		this.balance = balance;
		this.firstName = firstName;
		this.lastName = lastName;
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

	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Account))
			return false;
		Account other = (Account) o;
		return this.accountId == other.accountId && this.balance == other.balance&& this.firstName == other.firstName
				&& this.lastName == other.lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
