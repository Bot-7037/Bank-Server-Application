package com.cg.bankapplication.exceptions;

@SuppressWarnings("serial")
public class LowBalanceException extends Exception {
	public LowBalanceException(String errorString) {
		super(errorString);
	}
}
