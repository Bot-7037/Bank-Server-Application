package com.cg.bankapp.exceptions;

/**
 * Trying to transfer into same account
 * 
 * @author himanegi
 *
 */
@SuppressWarnings("serial")
public class SameAccountException extends Exception {
	public SameAccountException(String errorString) {
		super(errorString);
	}

}
