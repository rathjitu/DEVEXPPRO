package com.devexperts.exceptions;

import lombok.Getter;

public class InSufficientFundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
    private final String message;
 
    public InSufficientFundException(String message) {
        this.message = message;
    }
 
    public InSufficientFundException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }
 

 
}
