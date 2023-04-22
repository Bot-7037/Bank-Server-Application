package com.cg.bankapplication.exceptions;

@SuppressWarnings("serial")
public class SameAccountException extends Exception {
	public SameAccountException(String errorString) {
		super(errorString);
	}

}
