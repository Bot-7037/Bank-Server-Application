package com.cg.bankapp.services;

import java.util.List;
import java.util.stream.Collectors;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.dao.BankAppDAO;
import com.cg.bankapp.dao.BankAppDAOImpl;
import com.cg.bankapp.beans.TransactionType;
import com.cg.bankapp.utils.EntityManagerGenerator;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.AccountNotFoundException;

/**
 * Implementation for bank app services
 * 
 * @author himanegi
 *
 */
public class BankAppServicesImpl implements BankAppServices {

	private BankAppDAO dao = null;

	/**
	 * Protected constructor to be used during testing to mock the DAO layer
	 * 
	 * @param mockDao
	 */
	protected BankAppServicesImpl(BankAppDAO mockDao) {
		this.dao = mockDao;
	}

	public BankAppServicesImpl() {
		dao = new BankAppDAOImpl(EntityManagerGenerator.getEntityManager());
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
			throw new InvalidAmountException("Invalid amount");
		if (account.getBalance() < amount)
			throw new LowBalanceException("Insufficient balance");
		account.setBalance(account.getBalance() - amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setAccount(account);
		transaction.setBalance(account.getBalance());
		transaction.setTransactionType(TransactionType.WITHDRAW);

		dao.addTransactionToDatabase(transaction, null);

		return transaction;
	}

	@Override
	public Transaction deposit(int accountNumber, int amount) throws InvalidAmountException, AccountNotFoundException {

		Account account = dao.getAccountById(accountNumber);
		if (amount <= 0)
			throw new InvalidAmountException("Invalid amount");
		account.setBalance(account.getBalance() + amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setBalance(account.getBalance());
		transaction.setAccount(account);

		dao.addTransactionToDatabase(transaction, null);

		return transaction;
	}

	@Override
	public Transaction fundTransfer(int fromAccount, int toAccount, int amount)
			throws SameAccountException, InvalidAmountException, LowBalanceException, AccountNotFoundException {
		if (fromAccount == toAccount)
			throw new SameAccountException("Transferring to same account");
		if (amount < 0)
			throw new InvalidAmountException("Invalid amount");

		Account fromAccountObject = dao.getAccountById(fromAccount);
		Account toAccountObject = dao.getAccountById(toAccount);

		if (fromAccountObject.getBalance() < amount) {
			throw new LowBalanceException("Insufficient Funds");
		}

		toAccountObject.setBalance(toAccountObject.getBalance() + amount);
		fromAccountObject.setBalance(fromAccountObject.getBalance() - amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setAccount(fromAccountObject);
		transaction.setBalance(fromAccountObject.getBalance());
		transaction.setTransactionType(TransactionType.FUND_TRANSFER);

		Transaction transaction2 = new Transaction();
		transaction2.setAmount(amount);
		transaction2.setAccount(toAccountObject);
		transaction2.setBalance(toAccountObject.getBalance());
		transaction2.setTransactionType(TransactionType.DEPOSIT);

		dao.addTransactionToDatabase(transaction, transaction2);

		return transaction;
	}

	@Override
	public List<Transaction> showTransactions(int accountNumber) throws AccountNotFoundException {
		Account account = dao.getAccountById(accountNumber);
		// show all the transactions in sorted order newest to oldest (only 10)
		return account.getTransactions().stream().sorted((x, y) -> {
			return y.getTransactionDate().compareTo(x.getTransactionDate());
		}).limit(10).collect(Collectors.toList());
	}

}
