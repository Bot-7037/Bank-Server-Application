package com.cg.bankapp.services;

import java.util.List;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.beans.TransactionType;
import com.cg.bankapp.dao.BankAppDAO;
import com.cg.bankapp.dao.BankAppDAOImpl;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;
import com.cg.bankapp.utils.Database;
import com.cg.bankapp.utils.DatabaseConnection;

/**
 * Implementation for bank app services
 * 
 * @author himanegi
 *
 */
public class BankAppServicesImpl implements BankAppServices {

	private BankAppDAO dao = null;

	protected BankAppServicesImpl(BankAppDAO dao) {
		this.dao = dao;
	}

	public BankAppServicesImpl() {
		new Database();
		dao = new BankAppDAOImpl(DatabaseConnection.getConnection());
	}

	@Override
	public int showBalance(int accountNumber) throws AccountNotFoundException {
		Account account;
		account = dao.getAccountById(accountNumber);
		return account.getBalance();
	}

	@Override
	public Transaction withdraw(int accountNumber, int amount)
			throws LowBalanceException, InvalidAmountException, AccountNotFoundException {

		Account account = dao.getAccountById(accountNumber);
		if (amount <= 0)
			throw new InvalidAmountException("\nInvalid amount\n");
		if (account.getBalance() < amount)
			throw new LowBalanceException("\nInsufficient balance\n");
		account.setBalance(account.getBalance() - amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTransactionType(TransactionType.WITHDRAW);
		transaction.setBalance(account.getBalance());
		transaction.setFromAccount(accountNumber);
		transaction.setToAccount(-1);
		account.addTransaction(transaction);

		dao.updateAccount(accountNumber, account.getBalance());
		dao.addTransaction(transaction);

		return transaction;
	}

	@Override
	public Transaction deposit(int accountNumber, int amount) throws InvalidAmountException, AccountNotFoundException {

		Account account = dao.getAccountById(accountNumber);
		if (amount <= 0)
			throw new InvalidAmountException("\nInvalid amount\n");
		account.setBalance(account.getBalance() + amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setBalance(account.getBalance());
		transaction.setFromAccount(-1);
		transaction.setToAccount(accountNumber);
		account.addTransaction(transaction);

		dao.updateAccount(accountNumber, account.getBalance());
		dao.addTransaction(transaction);

		return transaction;
	}

	@Override
	public Transaction fundTransfer(int fromAccount, int toAccount, int amount)
			throws SameAccountException, InvalidAmountException, LowBalanceException, AccountNotFoundException {
		if (fromAccount == toAccount)
			throw new SameAccountException("\nTransferring to same account");
		if (amount < 0)
			throw new InvalidAmountException("\nInvalid amount");

		Account fromAccount1 = dao.getAccountById(fromAccount);
		Account toAccount2 = dao.getAccountById(toAccount);

		if (fromAccount1.getBalance() < amount)
			throw new LowBalanceException("\nInsufficient Funds");

		fromAccount1.setBalance(fromAccount1.getBalance() - amount);
		toAccount2.setBalance(toAccount2.getBalance() + amount);

		Transaction transaction = new Transaction();
		transaction.setBalance(fromAccount1.getBalance());
		transaction.setFromAccount(fromAccount);
		transaction.setTransactionType(TransactionType.FUND_TRANSFER);
		fromAccount1.addTransaction(transaction);

		Transaction transaction2 = transaction;
		transaction2.setToAccount(toAccount);
		transaction2.setBalance(toAccount2.getBalance());
		toAccount2.addTransaction(transaction2);

		dao.updateAccount(fromAccount, fromAccount1.getBalance());
		dao.updateAccount(toAccount, toAccount2.getBalance());
		dao.addTransaction(transaction);

		return transaction;
	}

	@Override
	public List<Transaction> showTransactions(int accountNumber) throws AccountNotFoundException {
		Account account = dao.getAccountById(accountNumber);
		return account.getTransactions();
	}

}
