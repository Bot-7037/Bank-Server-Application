package com.cg.bankapplication.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.cg.bankapplication.beans.Account;
import com.cg.bankapplication.beans.Customer;
import com.cg.bankapplication.beans.Transaction;
import com.cg.bankapplication.exceptions.AccountNotFoundException;
import com.cg.bankapplication.exceptions.InvalidAmountException;
import com.cg.bankapplication.exceptions.LowBalanceException;
import com.cg.bankapplication.exceptions.SameAccountException;
import com.cg.bankapplication.services.BankAppServices;
import com.cg.bankapplication.services.BankAppServicesImpl;
import com.cg.bankapplication.util.Database;

public class BankServiceTest {
	private static BankAppServices services = new BankAppServicesImpl();
	static int balanceCounter = 3000;

	@BeforeAll
	public static void setupTransactionCheck()
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException {
		Account account = new Account(1001, 0, null);
		account.setBalance(1000);
		account.setCustomer(new Customer(1001, "Name"));
		Database.accounts.put(1001, account);

		for (int i = 0; i < 10; i++) {
			services.deposit(1001, 200);
		}

	}

	@BeforeEach
	public void setupTest() throws LowBalanceException, AccountNotFoundException, InvalidAmountException {
		Account account = new Account(999, 0, null);
		account.setBalance(1000);
		account.setCustomer(new Customer(999, "Name"));
		Database.accounts.put(999, account);
	}

	@Test
	void testShowBalance_ValidAccount_ShouldBeEqual() throws AccountNotFoundException {
		assertEquals(1000, services.showBalance(999));
	}

	@Test
	void testShowBalance_InvalidAccount_ShouldThrowException() {
		assertThrows(AccountNotFoundException.class, () -> services.showBalance(99));
	}

	@Test
	void testWithdraw_ValidAmount_ShouldReduceAmount()
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException {
		services.withdraw(999, 200);
		assertEquals(800, Database.accounts.get(999).getBalance());
	}

	@Test
	void testWithdraw_InvalidAmount_ShouldThrowException() {
		assertThrows(InvalidAmountException.class, () -> services.withdraw(999, -10));
	}

	@Test
	void testWithdraw_InsufficientAmount_ShouldThrowException() {
		assertThrows(LowBalanceException.class, () -> services.withdraw(999, 100000000));
	}

	@Test
	void testWithdraw_InvalidAccount_ShouldThrowException() {
		assertThrows(AccountNotFoundException.class, () -> services.withdraw(99, 100));
	}

	@Test
	void testDeposit_ValidAmount_ShouldPass() throws AccountNotFoundException, InvalidAmountException {
		services.deposit(999, 200);
		assertEquals(1200, Database.accounts.get(999).getBalance());
	}

	@Test
	void testDeposit_InvalidAmount_ShouldThrowException() {
		assertThrows(InvalidAmountException.class, () -> services.deposit(999, -99));
	}

	@Test
	void testDeposit_InvalidAccount_ShouldThrowException() {
		assertThrows(AccountNotFoundException.class, () -> services.deposit(99, 100));
	}

	@Test
	void testFundTransfer_Valid_ShouldTransfer()
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException, SameAccountException {
		Account account2 = new Account(1000, 1000, new Customer(1000, "Name"));
		Database.accounts.put(1000, account2);
		services.fundTransfer(999, 1000, 100);

		assertEquals(900, Database.accounts.get(999).getBalance());
		assertEquals(1100, Database.accounts.get(1000).getBalance());
	}

	@Test
	void testFundTransfer_InsufficientFunds_ShouldThrowError()
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException, SameAccountException {
		Account account2 = new Account(1000, 1000, new Customer(1000, "Name"));
		Database.accounts.put(1000, account2);

		assertThrows(LowBalanceException.class, () -> services.fundTransfer(999, 1000, 100000));
	}

	@Test
	void testFundTransfer_InvalidRecieverAccount_ShouldThrowException() {
		assertThrows(AccountNotFoundException.class, () -> services.fundTransfer(999, 99, 100));
	}

	@Test
	void testFundTransfer_InvalidSenderAccount_ShouldThrowException() {
		assertThrows(AccountNotFoundException.class, () -> services.fundTransfer(99, 999, 100));
	}

	@Test
	void testFundTransfer_InvalidAmount_ShouldThrowException() {
		Account account2 = new Account(1000, 1000, new Customer(1000, "Name"));
		Database.accounts.put(1000, account2);

		assertThrows(InvalidAmountException.class, () -> services.fundTransfer(999, 1000, -100));
	}

	@Test
	void testFundTransfer_SameAccount_ShouldThrowException() {
		assertThrows(SameAccountException.class, () -> services.fundTransfer(999, 999, 100));
	}

	@ParameterizedTest
	@MethodSource("retrieveTransactions")
	void testShowTransactions_ValidTransactions_ShouldPass(Transaction transaction) {
		assertEquals(balanceCounter, transaction.getBalance());
		balanceCounter -= 200;
	}

	public static List<Transaction> retrieveTransactions() throws AccountNotFoundException {
		return services.showTransactions(1001);
	}

	@AfterAll
	public static void cleanupTest() {
		Database.accounts.remove(999);
		Database.accounts.remove(1001);
	}

}
