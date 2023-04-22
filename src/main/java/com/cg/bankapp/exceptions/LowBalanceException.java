package com.cg.bankapp.exceptions;

@SuppressWarnings("serial")
public class LowBalanceException extends Exception {

	public LowBalanceException() {
		super();
	}

	public LowBalanceException(String message) {
		super(message);
	}

}
