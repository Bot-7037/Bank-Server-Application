package com.cg.bankapp.exceptions;

@SuppressWarnings("serial")
public class InvalidAmountException extends Exception {

	public InvalidAmountException() {
		super();
	}

	public InvalidAmountException(String message) {
		super(message);
	}

}
