package com.cg.bankapp.exceptions;

/**
 * Insufficient balance
 * 
 * @author himanegi
 *
 */
@SuppressWarnings("serial")
public class LowBalanceException extends Exception {
	public LowBalanceException(String errorString) {
		super(errorString);
	}
}
