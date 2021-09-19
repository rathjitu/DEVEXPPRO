package com.devexperts.exceptions;

import lombok.Getter;

public class AccountNotExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
    private final String message;
 
    public AccountNotExistException(String message) {
        this.message = message;
    }
 
    public AccountNotExistException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }
 

 
}
