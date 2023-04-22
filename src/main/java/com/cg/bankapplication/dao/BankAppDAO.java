package com.cg.bankapplication.dao;

import com.cg.bankapplication.beans.Account;
import com.cg.bankapplication.exceptions.AccountNotFoundException;

/**
 * Data Access Object implementation to access the database through service
 * layer
 * 
 * @author himanegi
 *
 */
public interface BankAppDAO {

	/**
	 * Get an Account object from service layer and save it to the database.
	 * 
	 * @param account: account object from service layer
	 */
	abstract boolean saveAccount(Account account);

	/**
	 * Search for an account int the database. Run a loop that will search for given
	 * account in th database and return. Throw AccountNotFoundException if account
	 * does not exist.
	 * 
	 * @param accountNumber: Account number to search for in the database
	 */
	abstract Account getAccountByIdAccount(int accountNumber) throws AccountNotFoundException;
}
