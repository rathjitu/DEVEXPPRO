package com.devexperts.model.account;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize(contentAs= MoneyTransaction.class)
public class MoneyTransaction {
	public MoneyTransaction() {
	}

	public MoneyTransaction(Long accountFrom, Long accountTo, BigDecimal amount) {
		this.accountFromId = accountFrom;
		this.accountToId = accountTo;
		this.amount = amount;
	}

	private Long accountFromId;

	private Long accountToId;

	private BigDecimal amount;

	public Long getAccountFromId() {
		return accountFromId;
	}

	public void setAccountFromId(Long accountFromId) {
		this.accountFromId = accountFromId;
	}

	public Long getAccountToId() {
		return accountToId;
	}

	public void setAccountToId(Long accountToId) {
		this.accountToId = accountToId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "MoneyTransaction{" + "accountFrom=" + accountFromId + ", accountTo=" + accountToId + ", amount="
				+ amount + '}';
	}
}
