package com.cg.bankapplication.exceptions;

@SuppressWarnings("serial")
public class AccountNotFoundException extends Exception {
	public AccountNotFoundException(String errorString) {
		super(errorString);
	}
}
