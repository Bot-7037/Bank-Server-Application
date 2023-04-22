package com.cg.bankapp.dao;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;

public interface BankAppDAO {

	/**
	 * Save an account provided by service layer to the database Get an account, and
	 * run queries to add attributes to the database
	 * 
	 * @param account
	 * @return acknowledgement if saved or not
	 */
	boolean saveAccount(Account account);

	/**
	 * Get an account number and fetch attributes from the database, create an
	 * accout object and return to the caller
	 * 
	 * @param accountNumber
	 * @return account object
	 * @throws AccountNotFoundException
	 */
	Account getAccountById(int accountNumber) throws AccountNotFoundException;

	/**
	 * Update account details in the database for a particular account
	 * 
	 * @param accountNumber
	 * @param balance
	 * @return acknowledgement if account was updated or not
	 */
	boolean updateAccount(int accountNumber, int balance);

	/**
	 * Add a transaction to transaction table in database
	 * 
	 * @param transaction
	 * @return acknowledgement wether transaction was added or not
	 */
	boolean addTransaction(Transaction transaction);
}
