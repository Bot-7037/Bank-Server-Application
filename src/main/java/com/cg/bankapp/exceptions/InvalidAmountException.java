package com.cg.bankapp.exceptions;

/*
 * Invalid amount value requested
 */
@SuppressWarnings("serial")
public class InvalidAmountException extends Exception {
	public InvalidAmountException(String errorString) {
		super(errorString);
	}

	@Override
	public String toString() {
		return "InvalidAmountException";
	}
	
	
}
