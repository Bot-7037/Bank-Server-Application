package com.cg.bankapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.bankapp.dao.BankAppDAO;
import com.cg.bankapp.entity.AccountEntity;
import com.cg.bankapp.entity.TransactionEntity;
import com.cg.bankapp.entity.TransactionType;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.AccountNotFoundException;

@Service
public class BankAppServicesImpl implements BankAppServices {

	@Autowired
	private BankAppDAO dao;

	public BankAppServicesImpl() {

	}

	protected BankAppServicesImpl(BankAppDAO mockDao) {
		this.dao = mockDao;
	}

	@Override
	public int showBalance(int accountNumber) throws AccountNotFoundException {
		AccountEntity account;
		account = dao.getAccountById(accountNumber);
		return account.getBalance();
	}

	@Override
	@Transactional(value = "transactionManager")
	public TransactionEntity withdraw(int accountNumber, int amount)
			throws LowBalanceException, InvalidAmountException, AccountNotFoundException {

		AccountEntity account = dao.getAccountById(accountNumber);
		if (amount <= 0)
			throw new InvalidAmountException("Cant withdraw negative amount");
		if (account.getBalance() < amount)
			throw new LowBalanceException("Insufficient balance");
		account.setBalance(account.getBalance() - amount);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setAmount(amount);
		transaction.setAccount(account);
		transaction.setBalance(account.getBalance());
		transaction.setTransactionType(TransactionType.WITHDRAW);

		dao.addTransactionToDatabase(transaction, null);

		return transaction;
	}

	@Override
	@Transactional(value = "transactionManager")
	public TransactionEntity deposit(int accountNumber, int amount)
			throws InvalidAmountException, AccountNotFoundException {

		AccountEntity account = dao.getAccountById(accountNumber);
		if (amount <= 0)
			throw new InvalidAmountException("Cant deposit negative amount");
		account.setBalance(account.getBalance() + amount);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setAmount(amount);
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setBalance(account.getBalance());
		transaction.setAccount(account);

		dao.addTransactionToDatabase(transaction, null);

		return transaction;
	}

	@Override
	@Transactional(value = "transactionManager")
	public TransactionEntity fundTransfer(int fromAccount, int toAccount, int amount)
			throws SameAccountException, InvalidAmountException, LowBalanceException, AccountNotFoundException {
		if (fromAccount == toAccount)
			throw new SameAccountException("Transferring to same account");
		if (amount < 0)
			throw new InvalidAmountException("Cant transfer negative amount");

		AccountEntity fromAccountObject = dao.getAccountById(fromAccount);
		AccountEntity toAccountObject = dao.getAccountById(toAccount);

		if (fromAccountObject.getBalance() < amount) {
			throw new LowBalanceException("Insufficient Funds");
		}

		toAccountObject.setBalance(toAccountObject.getBalance() + amount);
		fromAccountObject.setBalance(fromAccountObject.getBalance() - amount);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setAmount(amount);
		transaction.setAccount(fromAccountObject);
		transaction.setBalance(fromAccountObject.getBalance());
		transaction.setTransactionType(TransactionType.FUND_TRANSFER);

		TransactionEntity transaction2 = new TransactionEntity();
		transaction2.setAmount(amount);
		transaction2.setAccount(toAccountObject);
		transaction2.setBalance(toAccountObject.getBalance());
		transaction2.setTransactionType(TransactionType.DEPOSIT);

		dao.addTransactionToDatabase(transaction, transaction2);

		return transaction;
	}

	@Override
	public List<TransactionEntity> showTransactions(int accountNumber) throws AccountNotFoundException {
		AccountEntity account = dao.getAccountById(accountNumber);
		return account.getTransactions().stream()
				.sorted((x, y) -> y.getTransactionDate().compareTo(x.getTransactionDate())).limit(10)
				.collect(Collectors.toList());
	}

}
