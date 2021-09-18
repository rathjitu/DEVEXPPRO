package com.devexperts.model.account;

public enum AccountKey {
	SAVING(1), CURRENT(2);

	private AccountKey(final int  i) {
	       this.value = i;
	  }

	private int  value;

	public int  getValue() {
        return value;
    }
}