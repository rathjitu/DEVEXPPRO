package com.devexperts.model.account;

import  java.util.Arrays;

public enum AccountKey {
	SAVING(1), CURRENT(2),CREDIT(3),WEALTH(4),INVEST(5),TRADE(6);

	private AccountKey(final long  i) {
	       this.value = i;
	  }

	private long  value;

	public long  getValue() {
        return value;
    }
	public static AccountKey getRandom() {
	      return values()[(int) (Math.random() * values().length)];
	}
	 /**
     * @return the AccountKey representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static AccountKey getType(long s) throws IllegalArgumentException {
        return Arrays.stream(AccountKey.values())
                .filter(v -> v.value==s)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
    }
	


}