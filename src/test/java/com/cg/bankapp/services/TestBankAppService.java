package com.cg.bankapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Customer;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.dao.BankAppDaoAccount;
import com.cg.bankapp.dao.BankAppDaoTransaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

class TestBankAppService {
	public BankAppDaoAccount mockAccountDao = mock(BankAppDaoAccount.class);
	public BankAppDaoTransaction mockTransactionDao = mock(BankAppDaoTransaction.class);

	@Test
	void testShowBalance_ValidAccount_ShouldBeEqual()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Account account = new Account(101, 1000, new Customer(999, "Test"));
		Optional<Account> opAccount = Optional.ofNullable(account);
		when(mockAccountDao.findById(101)).thenReturn(opAccount);
		assertEquals(1000, services.showBalace(101));
	}

	@Test
	void testShowBalance_InvalidAccount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Optional<Account> opAccount = Optional.ofNullable(null);
		when(mockAccountDao.findById(99)).thenReturn(opAccount);
		assertThrows(AccountNotFoundException.class, () -> services.showBalace(99));
	}

	@Test
	void testWithdraw_ValidAmount_ShouldReduceAmount() throws LowBalanceException, AccountNotFoundException,
			InvalidAmountException, ClassNotFoundException, SQLException, AccountNotFoundException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Account account = new Account(101, 1000, new Customer(999, "Test"));
		Optional<Account> opAccount = Optional.ofNullable(account);
		Account debitedAccount = new Account(101, 800, new Customer(999, "Test"));
		when(mockAccountDao.findById(102)).thenReturn(opAccount);
		when(mockAccountDao.save(Mockito.any(Account.class))).thenReturn(debitedAccount);
		assertEquals(800, services.withdrawAmount(102, 200));
	}

	@Test
	void testWithdraw_InvalidAmount_ShouldThrowException() {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		when(mockAccountDao.findById(1)).thenReturn(Optional.ofNullable(new Account()));
		assertThrows(InvalidAmountException.class, () -> services.withdrawAmount(1, -10));
	}

	@Test
	void testWithdraw_InvalidAccount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Optional<Account> opAccount = Optional.ofNullable(null);
		when(mockAccountDao.findById(99)).thenReturn(opAccount);
		assertThrows(AccountNotFoundException.class, () -> services.withdrawAmount(99, 100));
	}

	@Test
	void testDeposit_ValidAmount_ShouldPass()
			throws AccountNotFoundException, InvalidAmountException, ClassNotFoundException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Account account = new Account(104, 1000, new Customer(999, "Test"));
		Optional<Account> opAccount = Optional.ofNullable(account);
		Account creditedAccount = new Account(104, 1200, new Customer(999, "Test"));
		when(mockAccountDao.findById(104)).thenReturn(opAccount);
		when(mockAccountDao.save(Mockito.any(Account.class))).thenReturn(creditedAccount);
		assertEquals(1200, services.depositAmount(104, 200));
	}

	@Test
	void testDeposit_InvalidAmount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Account account = new Account(105, 1000, new Customer(999, "testUser"));
		Optional<Account> opAccount = Optional.ofNullable(account);
		when(mockAccountDao.findById(Mockito.anyInt())).thenReturn(opAccount);
		assertThrows(InvalidAmountException.class, () -> services.depositAmount(105, -99));
	}

	@Test
	void testDeposit_InvalidAccount_ShouldThrowException()
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Optional<Account> opAccount = Optional.ofNullable(null);
		when(mockAccountDao.findById(99)).thenReturn(opAccount);
		assertThrows(AccountNotFoundException.class, () -> services.depositAmount(99, 100));
	}

	@Test
	void testFundTransfer_ValidAccount_ShouldTransferFunds() throws AccountNotFoundException, ClassNotFoundException,
			AccountNotFoundException, LowBalanceException, InvalidAmountException, SameAccountException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Account senderAccount = new Account(101, 100, new Customer(1011, "sender"));
		Account recieverAccount = new Account(102, 0, new Customer(1012, "reciever"));
		Optional<Account> opSenderAccount = Optional.ofNullable(senderAccount);
		Optional<Account> opRecieverAccount = Optional.ofNullable(recieverAccount);
		Account debitedAccount = new Account(101, 50, new Customer(1011, "sender"));
		Account creditedAccount = new Account(102, 50, new Customer(1012, "reciever"));
		when(mockAccountDao.findById(101)).thenReturn(opSenderAccount);
		when(mockAccountDao.findById(102)).thenReturn(opRecieverAccount);
		when(mockAccountDao.save(opSenderAccount.get())).thenReturn(debitedAccount);
		when(mockAccountDao.save(opRecieverAccount.get())).thenReturn(creditedAccount);
		assertEquals(50, services.fundTransfer(101, 102, 50));
	}

	@Test
	void testFundTransfer_ForInvalidInputException() throws AccountNotFoundException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		when(mockAccountDao.findById(1)).thenReturn(Optional.ofNullable(new Account()));
		when(mockAccountDao.findById(2)).thenReturn(Optional.ofNullable(new Account()));
		assertThrows(InvalidAmountException.class, () -> services.fundTransfer(1, 2, -100));
	}

	@Test
	void testFundTransfer_ForInsufficientBalanceException() throws AccountNotFoundException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Account account = new Account(1, 100, new Customer(11, "testUser"));
		Account account2 = new Account(2, 100, new Customer(12, "testUser"));
		Optional<Account> opAccount1 = Optional.ofNullable(account);
		Optional<Account> opAccount2 = Optional.ofNullable(account2);
		when(mockAccountDao.findById(1)).thenReturn(opAccount1);
		when(mockAccountDao.findById(2)).thenReturn(opAccount2);
		assertThrows(LowBalanceException.class, () -> services.fundTransfer(1, 2, 200));
	}

	@Test
	void testFundTransfer_ForAccountNotFoundException() throws AccountNotFoundException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Optional<Account> opAccount = Optional.ofNullable(null);
		when(mockAccountDao.findById(1)).thenReturn(opAccount);
		assertThrows(AccountNotFoundException.class, () -> services.fundTransfer(1, 2, 200));
	}

	@Test
	void testShowLast10_ForValidAccount() throws ClassNotFoundException, AccountNotFoundException, SQLException {
		BankService services = new BankServiceImpl(mockAccountDao, mockTransactionDao);
		Account mockAccount = mock(Account.class);
		List<Transaction> dummyTransaction = new ArrayList<Transaction>();
		for (int i = 1; i <= 10; i++) {
			Transaction obj = new Transaction();
			obj.setAmount(i * 10);
			obj.setBalance(i * 100);
			obj.setTransactionType(null);
			obj.setTransactionId(i * 1000);
			obj.setTransactionDate(null);
			dummyTransaction.add(obj);
		}
		Optional<Account> opAccount = Optional.ofNullable(mockAccount);
		when(mockAccountDao.findById(10)).thenReturn(opAccount);
		when(mockTransactionDao.findAllByAccountNumber(10)).thenReturn(dummyTransaction);
		List<Transaction> actualTransaction = services.showLastTransactions(10);
		for (int i = 0; i < 10; i++) {
			assertEquals(dummyTransaction.get(i), actualTransaction.get(i));
		}
	}
}