package com.cg.bankapp.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Customer;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.dao.BankAppDAO;
import com.cg.bankapp.dao.BankAppDAOImpl;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

class BankServicesTest {
	public BankAppDAO mockDao = mock(BankAppDAOImpl.class);

	@Test
	void testShowBalance_ValidAccount_ShouldBeEqual()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);

		when(mockDao.getAccountById(101)).thenReturn(new Account(101, 1000, new Customer(999, "Test")));
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
		Account account = new Account(102, 1000, new Customer(999, "testUser"));
		BankAppServices services = new BankAppServicesImpl(mockDao);
		when(mockDao.getAccountById(102)).thenReturn(account);
		when(mockDao.addTransactionToDatabase(Mockito.any(Transaction.class), Mockito.any(Transaction.class)))
				.thenReturn(true);
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
		Account account = new Account(103, 1000, new Customer(999, "Test"));
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
		Account account = new Account(104, 1000, new Customer(999, "Test"));
		when(mockDao.getAccountById(104)).thenReturn(account);
		when(mockDao.addTransactionToDatabase(Mockito.any(Transaction.class), Mockito.any(Transaction.class)))
				.thenReturn(true);
		assertEquals(1200, services.deposit(104, 200).getBalance());
	}

	@Test
	void testDeposit_InvalidAmount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		Account account = new Account(105, 1000, new Customer(999, "testUser"));
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
		Account senderAccount = new Account(101, 100, new Customer(1011, "sender"));
		Account recieverAccount = new Account(102, 0, new Customer(1012, "reciever"));

		when(mockDao.getAccountById(101)).thenReturn(senderAccount);
		when(mockDao.getAccountById(102)).thenReturn(recieverAccount);
		when(mockDao.addTransactionToDatabase(Mockito.any(Transaction.class), Mockito.any(Transaction.class)))
				.thenReturn(true);

		assertEquals(50, services.fundTransfer(101, 102, 50).getBalance());
	}

	@Test
	void testFundTransfer_ForSameAccountException() throws AccountNotFoundException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		Account account = new Account();
		Customer customer = new Customer();

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
		Account account = new Account(1, 100, new Customer(11, "testUser"));
		Account account2 = new Account(2, 100, new Customer(12, "testUser"));

		when(mockDao.getAccountById(1)).thenReturn(account);
		when(mockDao.getAccountById(2)).thenReturn(account2);

		assertThrows(InvalidAmountException.class, () -> services.fundTransfer(1, 2, -100));
	}

	@Test
	void testFundTransfer_ForInsufficientBalanceException() throws AccountNotFoundException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		Account account = new Account(1, 100, new Customer(11, "testUser"));
		Account account2 = new Account(2, 100, new Customer(12, "testUser"));

		when(mockDao.getAccountById(1)).thenReturn(account);
		when(mockDao.getAccountById(2)).thenReturn(account2);

		assertThrows(LowBalanceException.class, () -> services.fundTransfer(1, 2, 200));
	}

	@Test
	void testFundTransfer_ForAccountNotFoundException() throws AccountNotFoundException {
		BankAppServices services = new BankAppServicesImpl(mockDao);
		when(mockDao.getAccountById(1)).thenThrow(AccountNotFoundException.class);

		assertThrows(AccountNotFoundException.class, () -> services.fundTransfer(1, 2, 200));
	}

}
