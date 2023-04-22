package com.cg.bankapp.dao;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;

public interface BankAppDAO {

	/**
	 * Save an account provided by service layer to the database. Recieves an
	 * account object from service layer and persists it to database.
	 * 
	 * @param account
	 * @return acknowledgement if saved or not
	 */
	boolean saveAccount(Account account);

	/**
	 * Get an account number and fetch all attributes including last 10 transactions
	 * from the database, create an accout object and return to the caller.
	 * 
	 * @param accountNumber
	 * @return account object
	 * @throws AccountNotFoundException
	 */
	Account getAccountById(int accountNumber) throws AccountNotFoundException;

	/**
	 * Update account details in the database for a particular account, recieves an
	 * account number and two transactions. In case of withdraw and deposit, the
	 * second transaction is nullable and must be handles using Optional class. If a
	 * transactions fails in middle for some reason, rollback
	 * 
	 * @param accountNumber
	 * @param balance
	 * @return acknowledgement if account was updated or not
	 * @throws AccountNotFoundException
	 */
	boolean addTransactionToDatabase(Transaction transaction, Transaction transaction2) throws AccountNotFoundException;
}
