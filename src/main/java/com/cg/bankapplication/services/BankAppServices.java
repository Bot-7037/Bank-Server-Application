package com.cg.bankapplication.services;

import java.util.List;

import com.cg.bankapplication.beans.Transaction;
import com.cg.bankapplication.exceptions.AccountNotFoundException;
import com.cg.bankapplication.exceptions.InvalidAmountException;
import com.cg.bankapplication.exceptions.LowBalanceException;
import com.cg.bankapplication.exceptions.SameAccountException;

/**
 * Services that the bank app would provide, interacts with Main and DAO
 * 
 * @author himanegi
 *
 */
public interface BankAppServices {
	/**
	 * get account object from the DAO and return balance of the account
	 *
	 * @param accountNumber
	 * @return
	 * @throws AccountNotFoundException
	 */
	abstract int showBalance(int accountNumber) throws AccountNotFoundException;

	/**
	 * get two account objects from DAO and perform fund transfer between the
	 * accounts
	 * 
	 * @param accountNumber1
	 * @param accountNumber2
	 * @param amount
	 * @return 
	 * @throws LowBalanceException
	 * @throws AccountNotFoundException
	 * @throws InvalidAmountException
	 * @throws SameAccountException
	 */
	abstract Transaction fundTransfer(int accountNumber1, int accountNumber2, int amount)
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException, SameAccountException;

	/**
	 * get account object from database usning DAO and update the balance and update
	 * the transactions
	 * 
	 * @param accountNumber
	 * @param amount
	 * @throws LowBalanceException
	 * @throws AccountNotFoundException
	 * @throws InvalidAmountException
	 */
	abstract Transaction withdraw(int accountNumber, int amount)
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException;

	/**
	 * 
	 * get account object from database usning DAO and update the balance and update
	 * the transactions
	 * 
	 * @param accountNumber
	 * @param amount
	 * @throws AccountNotFoundException
	 * @throws InvalidAmountException
	 * 
	 */
	abstract Transaction deposit(int accountNumber, int amount) throws AccountNotFoundException, InvalidAmountException;

	/**
	 * show last 10 transactions of an account
	 * 
	 * @param accountNumber
	 * @return
	 * @throws AccountNotFoundException
	 */
	abstract List<Transaction> showTransactions(int accountNumber) throws AccountNotFoundException;
}
