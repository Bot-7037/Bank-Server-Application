package com.cg.bankapp.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

@ControllerAdvice
public class Handler {

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleTypeMismatch() {
		return new ResponseEntity<>("Input Mismatch", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFound() {
		return new ResponseEntity<>("Account Does Not Exist", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidAmountException.class)
	public ResponseEntity<String> handleNegativeAmount() {
		return new ResponseEntity<>("Negative amount entered", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SameAccountException.class)
	public ResponseEntity<String> handleSameAccount() {
		return new ResponseEntity<>("Trying to transfer to same account", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LowBalanceException.class)
	public ResponseEntity<String> handleLowBalanceException() {
		return new ResponseEntity<>("Insufficient funds", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<String> handleNumberFormatException(){
		return new ResponseEntity<>("Invalid input format", HttpStatus.BAD_REQUEST);
	}
}
