package com.cg.bankapp.services;

import java.util.List;

import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

public interface BankAppServices {
	/**
	 * Get an account from database and return the balance
	 * 
	 * @param accountNumber
	 * @return account.getBalance()
	 * @throws AccountNotFoundException
	 */
	int showBalance(int accountNumber) throws AccountNotFoundException;

	/**
	 * Withdraw money from an account
	 * 
	 * @param accountNumber
	 * @param amount
	 * @return transaction regarding the withdraw
	 * @throws LowBalanceException
	 * @throws InvalidAmountException
	 * @throws AccountNotFoundException
	 */
	Transaction withdraw(int accountNumber, int amount)
			throws LowBalanceException, InvalidAmountException, AccountNotFoundException;

	/**
	 * Function to deposit money into account
	 * 
	 * @param accountNumber
	 * @param amount
	 * @return transation regarding deposit
	 * @throws InvalidAmountException
	 * @throws AccountNotFoundException
	 */
	Transaction deposit(int accountNumber, int amount) throws InvalidAmountException, AccountNotFoundException;

	/**
	 * Transfer money from one account to another
	 * 
	 * @param fromAccount
	 * @param toAccount
	 * @param amount
	 * @return trsansaction regarding fund transfer
	 * @throws SameAccountException
	 * @throws InvalidAmountException
	 * @throws LowBalanceException
	 * @throws AccountNotFoundException
	 */
	Transaction fundTransfer(int fromAccount, int toAccount, int amount)
			throws SameAccountException, InvalidAmountException, LowBalanceException, AccountNotFoundException;

	/**
	 * Show last 10 transactions of an account
	 * 
	 * @param accountNumber
	 * @return
	 * @throws AccountNotFoundException
	 */
	public List<Transaction> showTransactions(int accountNumber) throws AccountNotFoundException;

}
