package com.cg.bankapplication.exceptions;

@SuppressWarnings("serial")
public class InvalidAmountException extends Exception{
	public InvalidAmountException(String errorString) {
		super(errorString);
	}
}
