package com.cg.bankapplication.dao;

import com.cg.bankapplication.beans.Account;
import com.cg.bankapplication.exceptions.AccountNotFoundException;
import com.cg.bankapplication.util.Database;

/**
 * Implementation for Data Access Object
 * 
 * @author himanegi
 *
 */
public class BankAppDAOImpl implements BankAppDAO {

	@Override
	public boolean saveAccount(Account account) {
		Database.accounts.putIfAbsent(account.getAccountNumber(), account);
		return Database.accounts.containsKey(account.getAccountNumber());
	}

	@Override
	public Account getAccountByIdAccount(int accountNumber) throws AccountNotFoundException {

		if (Database.accounts.containsKey(accountNumber)) {
			return Database.accounts.get(accountNumber);
		} else {
			throw new AccountNotFoundException("\nInvalid Account Number");
		}
	}
}
