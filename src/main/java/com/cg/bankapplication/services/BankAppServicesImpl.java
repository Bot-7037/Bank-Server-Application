package com.cg.bankapplication.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.cg.bankapplication.beans.Account;
import com.cg.bankapplication.beans.Transaction;
import com.cg.bankapplication.beans.TransactionType;
import com.cg.bankapplication.dao.BankAppDAO;
import com.cg.bankapplication.dao.BankAppDAOImpl;
import com.cg.bankapplication.exceptions.AccountNotFoundException;
import com.cg.bankapplication.exceptions.InvalidAmountException;
import com.cg.bankapplication.exceptions.LowBalanceException;
import com.cg.bankapplication.exceptions.SameAccountException;

/**
 * Implementation for bank app services
 * 
 * @author himanegi
 *
 */
public class BankAppServicesImpl implements BankAppServices {

	@Override
	public int showBalance(int accountNumber) throws AccountNotFoundException {
		BankAppDAO dao = new BankAppDAOImpl();
		Account account;
		account = dao.getAccountByIdAccount(accountNumber);
		return account.getBalance();
	}

	@Override
	public Transaction fundTransfer(int fromAccount, int toAccount, int amount)
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException, SameAccountException {

		if (fromAccount == toAccount)
			throw new SameAccountException("\nTransferring to same account");

		if (amount < 0)
			throw new InvalidAmountException("\nInvalid amount");
		
		BankAppDAO dao = new BankAppDAOImpl();
		Account fromAccount1 = dao.getAccountByIdAccount(fromAccount);
		Account toAccount2 = dao.getAccountByIdAccount(toAccount);

		if (fromAccount1.getBalance() < amount)
			throw new LowBalanceException("\nInsufficient Funds");
		
		fromAccount1.setBalance(fromAccount1.getBalance() - amount);
		toAccount2.setBalance(toAccount2.getBalance() + amount);

		// Create two transactions for sender and reciever and add to respective accounts
		Transaction transaction = new Transaction();
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		transaction.setDateString(dateTimeFormatter.format(now));
		
		transaction.setAmount(amount);
		transaction.setBalance(fromAccount1.getBalance());
		transaction.setTransactionType(TransactionType.FUND_TRANSFER);

		fromAccount1.addTransaction(transaction);

		Transaction transaction2 = transaction;
		transaction2.setBalance(toAccount2.getBalance());
		toAccount2.addTransaction(transaction2);

		return transaction;
	}

	@Override
	public Transaction withdraw(int accountNumber, int amount)
			throws LowBalanceException, AccountNotFoundException, InvalidAmountException {
		BankAppDAO dao = new BankAppDAOImpl();
		Account account = dao.getAccountByIdAccount(accountNumber);
		if (amount <= 0)
			throw new InvalidAmountException("\nInvalid amount");

		if (account.getBalance() < amount)
			throw new LowBalanceException("\nInsufficient Funds");
		account.setBalance(account.getBalance() - amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTransactionType(TransactionType.WITHDRAW);
		transaction.setBalance(account.getBalance());

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		transaction.setDateString(dateTimeFormatter.format(now));
		
		account.addTransaction(transaction);

		return transaction;
	}

	@Override
	public Transaction deposit(int accountNumber, int amount) throws AccountNotFoundException, InvalidAmountException {
		if (amount <= 0) {
			throw new InvalidAmountException("\nInvalid Amount");
		}
		BankAppDAO dao = new BankAppDAOImpl();
		Account account = dao.getAccountByIdAccount(accountNumber);
		account.setBalance(account.getBalance() + amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setBalance(account.getBalance());
		transaction.setTransactionType(TransactionType.DEPOSIT);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		transaction.setDateString(dateTimeFormatter.format(now));

		account.addTransaction(transaction);

		return transaction;
	}

	@Override
	public List<Transaction> showTransactions(int accountNumber) throws AccountNotFoundException {
		BankAppDAO dao = new BankAppDAOImpl();
		Account account = dao.getAccountByIdAccount(accountNumber);
		return account.getTransactions();
	}

}
