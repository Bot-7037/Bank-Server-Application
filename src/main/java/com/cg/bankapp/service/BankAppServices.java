package com.cg.bankapp.service;

import java.util.List;

import com.cg.bankapp.entity.TransactionEntity;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

public interface BankAppServices {
	/**
	 * Get an account from database and return the balance attribute to the caller
	 * 
	 * @param accountNumber
	 * @return account.getBalance()
	 * @throws AccountNotFoundException
	 */
	int showBalance(int accountNumber) throws AccountNotFoundException;

	/**
	 * Withdraw money from an account. Recieves account number and amount and makes
	 * a call to DAO layer to perform respective transactions.
	 * 
	 * @param accountNumber
	 * @param amount
	 * @return transaction regarding the withdraw
	 * @throws LowBalanceException
	 * @throws InvalidAmountException
	 * @throws AccountNotFoundException
	 */
	TransactionEntity withdraw(int accountNumber, int amount)
			throws LowBalanceException, InvalidAmountException, AccountNotFoundException;

	/**
	 * Function to deposit money into account. Recieves account number and amount
	 * and makes a call to DAO layer to perform respective transactions.
	 * 
	 * @param accountNumber
	 * @param amount
	 * @return transation regarding deposit
	 * @throws InvalidAmountException
	 * @throws AccountNotFoundException
	 */
	TransactionEntity deposit(int accountNumber, int amount) throws InvalidAmountException, AccountNotFoundException;

	/**
	 * Transfer money from one account to another
	 * 
	 * @param fromAccount
	 * @param toAccount
	 * @param amount
	 * @return transaction regarding fund transfer
	 * @throws SameAccountException
	 * @throws InvalidAmountException
	 * @throws LowBalanceException
	 * @throws AccountNotFoundException
	 */
	TransactionEntity fundTransfer(int fromAccount, int toAccount, int amount)
			throws SameAccountException, InvalidAmountException, LowBalanceException, AccountNotFoundException;

	/**
	 * Show last 10 transactions of an account. Make a call to DAO layer to find
	 * Account object from database and return the list of transactions to the
	 * caller
	 * 
	 * @param accountNumber
	 * @return list of transactions related to the account
	 * @throws AccountNotFoundException
	 */
	public List<TransactionEntity> showTransactions(int accountNumber) throws AccountNotFoundException;

}
