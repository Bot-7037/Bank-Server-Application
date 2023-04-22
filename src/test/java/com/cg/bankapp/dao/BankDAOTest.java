package com.cg.bankapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import com.cg.bankapp.entity.AccountEntity;
import com.cg.bankapp.entity.CustomerEntity;
import com.cg.bankapp.entity.TransactionEntity;
import com.cg.bankapp.exceptions.AccountNotFoundException;

class BankDAOTest {
	private EntityManager entityManager = mock(EntityManager.class);

	@Test
	void testSaveAccount_ForValidAccount() throws AccountNotFoundException {
		BankAppDAO dao = new BankAppDAOImpl(entityManager);
		AccountEntity account = new AccountEntity(1, 100, new CustomerEntity(101, "testUser"));
		assertEquals(1, dao.saveAccount(account));
	}

	@Test
	void testGetAccountById_ForAccountNotFoundException() {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		when(entityManager.find(AccountEntity.class, 99)).thenReturn(null);

		assertThrows(AccountNotFoundException.class, () -> bank.getAccountById(99));
	}

	@Test
	void testPerformTransaction_ForSingleValidTransaction() throws AccountNotFoundException {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		AccountEntity account = new AccountEntity(11, 1000, new CustomerEntity(111, "testUser"));

		TransactionEntity transaction = new TransactionEntity();
		transaction.setTransactionId(1);
		transaction.setAmount(100);
		transaction.setTransactionDate(null);
		transaction.setBalance(1100);
		transaction.setAccount(account);

		when(entityManager.find(AccountEntity.class, 11)).thenReturn(account);

		bank.addTransactionToDatabase(transaction, null);

		assertEquals(1100, account.getBalance());
	}

	@Test
	void testPerformTransaction_ForDoubleTransaction() throws AccountNotFoundException {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);
		AccountEntity senderAccount = new AccountEntity(11, 1000, new CustomerEntity(111, "sender"));
		AccountEntity recieverAccount = new AccountEntity(12, 1000, new CustomerEntity(112, "reciever"));
		senderAccount.setTransaction(senderAccount.getTransactions());

		TransactionEntity transaction = new TransactionEntity();
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

		TransactionEntity recieverTransaction = new TransactionEntity();
		recieverTransaction.setTransactionId(2);
		recieverTransaction.setAmount(100);
		recieverTransaction.setTransactionDate(null);
		recieverTransaction.setBalance(1100);
		recieverTransaction.setAccount(recieverAccount);

		when(entityManager.find(AccountEntity.class, 11)).thenReturn(senderAccount);
		when(entityManager.find(AccountEntity.class, 12)).thenReturn(recieverAccount);

		bank.addTransactionToDatabase(transaction, recieverTransaction);
		assertEquals(900, senderAccount.getBalance());
		assertEquals(1100, recieverAccount.getBalance());
	}

	@Test
	void testPerformTransaction_ForSenderAccountNotFoundException() {
		BankAppDAO bank = new BankAppDAOImpl(entityManager);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setTransactionId(1);
		transaction.setAmount(100);
		transaction.setTransactionDate(null);
		transaction.setBalance(1100);
		transaction.setAccount(new AccountEntity(11, 99, null));
		when(entityManager.find(AccountEntity.class, 11)).thenReturn(null);

		assertThrows(AccountNotFoundException.class, () -> bank.addTransactionToDatabase(transaction, null));
	}

	@Test
	void testPerformTransaction_ForRecieverAccountNotFoundException() {
		new BankAppDAOImpl(); // test constructor
		BankAppDAO bank = new BankAppDAOImpl(entityManager);

		TransactionEntity senderTransaction = new TransactionEntity();
		senderTransaction.setTransactionId(1);
		senderTransaction.setAmount(100);
		senderTransaction.setBalance(1100);
		senderTransaction.setAccount(new AccountEntity(11, 99, null));

		TransactionEntity recieverTransaction = new TransactionEntity();
		recieverTransaction.setTransactionId(1);
		recieverTransaction.setAmount(100);
		recieverTransaction.setBalance(1100);
		recieverTransaction.setAccount(new AccountEntity(12, 99, null));

		when(entityManager.find(AccountEntity.class, 11)).thenReturn(new AccountEntity(11, 99, null));
		when(entityManager.find(AccountEntity.class, 12)).thenReturn(null);

		assertThrows(AccountNotFoundException.class,
				() -> bank.addTransactionToDatabase(senderTransaction, recieverTransaction));
	}

}
