package com.cg.bankapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Customer;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

class BankDAOTest {
	private EntityManager entityManager = mock(EntityManager.class);

	@Test
	void testSaveAccount_ForValidAccount() throws AccountNotFoundException {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		EntityTransaction et = mock(EntityTransaction.class);
		Account account = new Account(11, 100, new Customer(101, "testUser"));
		when(entityManager.getTransaction()).thenReturn(et);
		assertTrue(bank.saveAccount(account));
	}

	@Test
	void testGetAccountById_ForAccountNotFoundException() {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		when(entityManager.find(Account.class, 99)).thenReturn(null);

		assertThrows(AccountNotFoundException.class, () -> bank.getAccountById(99));
	}

	@Test
	void testPerformTransaction_ForSingleValidTransaction() throws AccountNotFoundException {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		Account account = new Account(11, 1000, new Customer(111, "testUser"));

		Transaction transaction = new Transaction();
		transaction.setTransactionId(1);
		transaction.setAmount(100);
		transaction.setTransactionDate(null);
		transaction.setBalance(1100);
		transaction.setAccount(account);

		EntityTransaction et = mock(EntityTransaction.class);

		when(entityManager.getTransaction()).thenReturn(et);
		when(entityManager.find(Account.class, 11)).thenReturn(account);

		bank.addTransactionToDatabase(transaction, null);

		assertEquals(1100, account.getBalance());
	}

	@Test
	void testPerformTransaction_ForDoubleTransaction() throws AccountNotFoundException {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		Account senderAccount = new Account(11, 1000, new Customer(111, "sender"));
		Account recieverAccount = new Account(12, 1000, new Customer(112, "reciever"));
		senderAccount.setTransaction(senderAccount.getTransactions());

		Transaction transaction = new Transaction();
		transaction.setTransactionId(1);
		transaction.setAmount(100);
		transaction.setTransactionDate(null);
		transaction.setBalance(900);
		transaction.setAccount(senderAccount);
		transaction.toString();
		transaction.getTransactionId();
		transaction.getTransactionType();
		transaction.getAmount();
		senderAccount.addTransaction(transaction);
		senderAccount.removeTransaction(transaction);
		senderAccount.addTransaction(transaction);

		Transaction recieverTransaction = new Transaction();
		recieverTransaction.setTransactionId(2);
		recieverTransaction.setAmount(100);
		recieverTransaction.setTransactionDate(null);
		recieverTransaction.setBalance(1100);
		recieverTransaction.setAccount(recieverAccount);

		EntityTransaction et = mock(EntityTransaction.class);

		when(entityManager.getTransaction()).thenReturn(et);
		when(entityManager.find(Account.class, 11)).thenReturn(senderAccount);
		when(entityManager.find(Account.class, 12)).thenReturn(recieverAccount);

		bank.addTransactionToDatabase(transaction, recieverTransaction);
		assertEquals(900, senderAccount.getBalance());
		assertEquals(1100, recieverAccount.getBalance());
	}

	@Test
	void testPerformTransaction_ForSenderAccountNotFoundException() {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		EntityTransaction et = mock(EntityTransaction.class);

		Transaction transaction = new Transaction();
		transaction.setTransactionId(1);
		transaction.setAmount(100);
		transaction.setTransactionDate(null);
		transaction.setBalance(1100);
		transaction.setAccount(new Account(11, 99, null));
		when(entityManager.getTransaction()).thenReturn(et);
		when(entityManager.find(Account.class, 11)).thenReturn(null);

		assertThrows(AccountNotFoundException.class, () -> bank.addTransactionToDatabase(transaction, null));
	}

	@Test
	void testPerformTransaction_ForRecieverAccountNotFoundException() {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		EntityTransaction et = mock(EntityTransaction.class);

		Transaction senderTransaction = new Transaction();
		senderTransaction.setTransactionId(1);
		senderTransaction.setAmount(100);
		senderTransaction.setBalance(1100);
		senderTransaction.setAccount(new Account(11, 99, null));

		Transaction recieverTransaction = new Transaction();
		recieverTransaction.setTransactionId(1);
		recieverTransaction.setAmount(100);
		recieverTransaction.setBalance(1100);
		recieverTransaction.setAccount(new Account(12, 99, null));

		when(entityManager.getTransaction()).thenReturn(et);
		when(entityManager.find(Account.class, 11)).thenReturn(new Account(11, 99, null));
		when(entityManager.find(Account.class, 12)).thenReturn(null);
		when(et.isActive()).thenReturn(true);

		assertThrows(AccountNotFoundException.class,
				() -> bank.addTransactionToDatabase(senderTransaction, recieverTransaction));
	}
}
