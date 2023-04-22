package com.cg.bankapp.exceptions;

/**
 * Exception when an account was not found in database
 * 
 * @author himanegi
 *
 */
@SuppressWarnings("serial")
public class AccountNotFoundException extends Exception {
	public AccountNotFoundException(String errorString) {
		super(errorString);
	}
}
