package com.cg.bankapp.exceptions;

@SuppressWarnings("serial")
public class SameAccountException extends Exception {

	public SameAccountException() {
		super();
	}

	public SameAccountException(String message) {
		super(message);
	}

}
