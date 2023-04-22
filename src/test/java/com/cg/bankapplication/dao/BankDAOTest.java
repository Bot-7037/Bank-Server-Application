package com.cg.bankapplication.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.cg.bankapplication.beans.Account;
import com.cg.bankapplication.beans.Customer;
import com.cg.bankapplication.exceptions.AccountNotFoundException;
import com.cg.bankapplication.util.Database;

class BankDAOTest {

	@Test
	void testSaveAccount_ValidAccount_ShouldBeSameObject() {
		Account account = new Account(999, 0, null);
		account.setBalance(1000);
		account.setCustomer(new Customer());
		BankAppDAO dao = new BankAppDAOImpl();
		dao.saveAccount(account);

		assertEquals(Database.accounts.get(999), account);
	}

	@Test
	void testGetAccountById_InvalidAccount_ShouldThrowException() {
		Account account = new Account(999, 0, null);
		account.setBalance(1000);
		account.setCustomer(new Customer());
		BankAppDAO dao = new BankAppDAOImpl();
		Database.accounts.put(999, account);

		assertThrows(AccountNotFoundException.class, () -> dao.getAccountByIdAccount(99));
	}

	@Test
	void testGetAccountById_ValidAccount_ShouldNotBeNull() throws AccountNotFoundException {
		Account account = new Account(999, 0, null);
		account.setBalance(1000);
		account.setCustomer(new Customer());
		BankAppDAO dao = new BankAppDAOImpl();
		Database.accounts.put(999, account);

		assertNotNull(dao.getAccountByIdAccount(999));
	}

	@Test
	void testGetAccountById_ValidAccount_ShouldGetCorrectObject() throws AccountNotFoundException {
		Account account = new Account(999, 0, null);
		account.setBalance(1000);
		account.setCustomer(new Customer());
		BankAppDAO dao = new BankAppDAOImpl();
		Database.accounts.put(999, account);

		assertSame(account, dao.getAccountByIdAccount(999));
	}

	@AfterEach
	public void cleanupTest() {
	}

}
