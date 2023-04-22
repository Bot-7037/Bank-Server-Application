package com.cg.bankapp.service;

import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.cg.bankapp.dao.BankAppDAO;
import com.cg.bankapp.dao.BankAppDAOImpl;
import com.cg.bankapp.entity.AccountEntity;
import com.cg.bankapp.entity.CustomerEntity;
import com.cg.bankapp.entity.TransactionEntity;
import static org.junit.jupiter.api.Assertions.*;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

class BankServiceTest {
	public BankAppDAO mockDao = mock(BankAppDAOImpl.class);

	@Test
	void testShowBalance_ValidAccount_ShouldBeEqual()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);

		when(mockDao.getAccountById(101)).thenReturn(new AccountEntity(101, 1000, new CustomerEntity(999, "Test")));
		assertEquals(1000, services.showBalance(101));
	}

	@Test
	void testShowBalance_InvalidAccount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		when(mockDao.getAccountById(Mockito.anyInt())).thenThrow(AccountNotFoundException.class);
		assertThrows(AccountNotFoundException.class, () -> services.showBalance(99));
	}

	@Test
	void testWithdraw_ValidAmount_ShouldReduceAmount() throws LowBalanceException, AccountNotFoundException,
			ClassNotFoundException, SQLException, AccountNotFoundException, InvalidAmountException {
		AccountEntity account = new AccountEntity(102, 1000, new CustomerEntity(999, "testUser"));
		BankAppServices services = new BankAppServicesImpl(mockDao);
		when(mockDao.getAccountById(102)).thenReturn(account);
		when(mockDao.addTransactionToDatabase(Mockito.any(TransactionEntity.class),
				Mockito.any(TransactionEntity.class))).thenReturn(true);
		assertEquals(800, services.withdraw(102, 200).getBalance());
	}

	@Test
	void testWithdraw_InvalidAmount_ShouldThrowException() {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		assertThrows(InvalidAmountException.class, () -> services.withdraw(1, -10));
	}

	@Test
	void testWithdraw_InsufficientAmount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		AccountEntity account = new AccountEntity(103, 1000, new CustomerEntity(999, "Test"));
		when(mockDao.getAccountById(103)).thenReturn(account);
		assertThrows(LowBalanceException.class, () -> services.withdraw(103, 100000000));
	}

	@Test
	void testWithdraw_InvalidAccount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		when(mockDao.getAccountById(99)).thenThrow(AccountNotFoundException.class);
		assertThrows(AccountNotFoundException.class, () -> services.withdraw(99, 100));
	}

	@Test
	void testDeposit_ValidAmount_ShouldPass()
			throws AccountNotFoundException, InvalidAmountException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		AccountEntity account = new AccountEntity(104, 1000, new CustomerEntity(999, "Test"));
		when(mockDao.getAccountById(104)).thenReturn(account);
		when(mockDao.addTransactionToDatabase(Mockito.any(TransactionEntity.class),
				Mockito.any(TransactionEntity.class))).thenReturn(true);
		assertEquals(1200, services.deposit(104, 200).getBalance());
	}

	@Test
	void testDeposit_InvalidAmount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		AccountEntity account = new AccountEntity(105, 1000, new CustomerEntity(999, "testUser"));
		when(mockDao.getAccountById(Mockito.anyInt())).thenReturn(account);
		assertThrows(InvalidAmountException.class, () -> services.deposit(105, -99));
	}

	@Test
	void testDeposit_InvalidAccount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		when(mockDao.getAccountById(99)).thenThrow(AccountNotFoundException.class);
		assertThrows(AccountNotFoundException.class, () -> services.deposit(99, 100));
	}

	@Test
	void testFundTransfer_ValidAccount_ShouldTransferFunds() throws AccountNotFoundException, ClassNotFoundException,
			AccountNotFoundException, LowBalanceException, InvalidAmountException, SameAccountException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		AccountEntity senderAccount = new AccountEntity(101, 100, new CustomerEntity(1011, "sender"));
		AccountEntity recieverAccount = new AccountEntity(102, 0, new CustomerEntity(1012, "reciever"));

		when(mockDao.getAccountById(101)).thenReturn(senderAccount);
		when(mockDao.getAccountById(102)).thenReturn(recieverAccount);
		when(mockDao.addTransactionToDatabase(Mockito.any(TransactionEntity.class),
				Mockito.any(TransactionEntity.class))).thenReturn(true);

		assertEquals(50, services.fundTransfer(101, 102, 50).getBalance());
	}

	@Test
	void testFundTransfer_ForSameAccountException() throws AccountNotFoundException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		AccountEntity account = new AccountEntity();
		CustomerEntity customer = new CustomerEntity();

		customer.setCustomerId(11);
		customer.setCustomerName("testUser");

		account.setAccountNumber(1);
		account.setBalance(100);
		account.setCustomer(customer);

		when(mockDao.getAccountById(1)).thenReturn(account);

		assertThrows(SameAccountException.class, () -> services.fundTransfer(1, 1, 100));
	}

	@Test
	void testFundTransfer_ForInvalidInputException() throws AccountNotFoundException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		AccountEntity account = new AccountEntity(1, 100, new CustomerEntity(11, "testUser"));
		AccountEntity account2 = new AccountEntity(2, 100, new CustomerEntity(12, "testUser"));

		when(mockDao.getAccountById(1)).thenReturn(account);
		when(mockDao.getAccountById(2)).thenReturn(account2);

		assertThrows(InvalidAmountException.class, () -> services.fundTransfer(1, 2, -100));
	}

	@Test
	void testFundTransfer_ForInsufficientBalanceException() throws AccountNotFoundException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		AccountEntity account = new AccountEntity(1, 100, new CustomerEntity(11, "testUser"));
		AccountEntity account2 = new AccountEntity(2, 100, new CustomerEntity(12, "testUser"));

		when(mockDao.getAccountById(1)).thenReturn(account);
		when(mockDao.getAccountById(2)).thenReturn(account2);

		assertThrows(LowBalanceException.class, () -> services.fundTransfer(1, 2, 200));
	}

	@Test
	void testFundTransfer_ForAccountNotFoundException() throws AccountNotFoundException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		when(mockDao.getAccountById(1)).thenThrow(AccountNotFoundException.class);

		new BankAppServicesImpl(); // Test Constructor
		AccountEntity accountEntity = new AccountEntity(2, 100, new CustomerEntity());
		when(mockDao.getAccountById(2)).thenReturn(accountEntity);
		services.showTransactions(2);

		assertThrows(AccountNotFoundException.class, () -> services.fundTransfer(1, 2, 200));
	}

}
