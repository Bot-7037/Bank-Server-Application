package com.cg.bankapp.services;

import java.util.List;

import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

/**
 * Service class to serve the requests from controller
 * 
 * @author himanegi
 *
 */
public interface BankService {

	/**
	 * return balance of an account
	 * 
	 * @param id
	 * @return balance
	 * @throws AccountNotFoundException
	 */
	public double showBalace(int id) throws AccountNotFoundException;

	/**
	 * Deposit money to an account
	 * 
	 * @param id
	 * @param amount
	 * @return final balance
	 * @throws AccountNotFoundException
	 * @throws InvalidAmountException
	 */
	public double depositAmount(int id, double amount) throws AccountNotFoundException, InvalidAmountException;

	/**
	 * Withdraw money from an account
	 * 
	 * @param id
	 * @param amount
	 * @return final balance of account
	 * @throws AccountNotFoundException
	 * @throws InvalidAmountException
	 * @throws LowBalanceException
	 */
	public double withdrawAmount(int id, double amount)
			throws AccountNotFoundException, InvalidAmountException, LowBalanceException;

	/**
	 * Transfer money from one account to another
	 * 
	 * @param senderId
	 * @param recieverId
	 * @param amount
	 * @return final balance of sender
	 * @throws AccountNotFoundException
	 * @throws LowBalanceException
	 * @throws SameAccountException
	 * @throws InvalidAmountException
	 */
	public double fundTransfer(int senderId, int recieverId, double amount)
			throws AccountNotFoundException, LowBalanceException, SameAccountException, InvalidAmountException;

	/**
	 * Show last ten transactions of an account from newest to oldest
	 * 
	 * @param id
	 * @return list of transactions
	 * @throws AccountNotFoundException
	 */
	public List<Transaction> showLastTransactions(int id) throws AccountNotFoundException;

}