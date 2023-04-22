package com.cg.bankapp.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.beans.TransactionType;
import com.cg.bankapp.dao.BankAppDaoAccount;
import com.cg.bankapp.dao.BankAppDaoTransaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;

@Component("AccountService")
public class BankServiceImpl implements BankService {

	@Autowired
	private BankAppDaoAccount accountDao;

	@Autowired
	private BankAppDaoTransaction transcationDao;

	protected BankServiceImpl(BankAppDaoAccount accountDao, BankAppDaoTransaction transactionDao) {
		this.accountDao = accountDao;
		this.transcationDao = transactionDao;
	}

	@Override
	public double showBalace(int id) throws AccountNotFoundException {

		Account account = accountDao.findById(id).orElse(null);

		if (account == null)
			throw new AccountNotFoundException("Account " + id + "not found");

		return account.getBalance();
	}

	@Override
	@Transactional
	public double depositAmount(int id, double amount) throws AccountNotFoundException, InvalidAmountException {
		Account account = accountDao.findById(id).orElse(null);

		if (account == null)
			throw new AccountNotFoundException("Account " + id + "not found");

		if (amount < 0)
			throw new InvalidAmountException("Invalid amount entered");

		Transaction transaction = new Transaction();

		transaction.setAccount(account);
		transaction.setAmount(amount);
		transaction.setBalance(account.getBalance() + amount);
		transaction.setTransactionType(TransactionType.DEPOSIT);

		account.setBalance(account.getBalance() + amount);
		account.addTransaction(transaction);

		accountDao.save(account);

		return transaction.getBalance();
	}

	@Override
	@Transactional
	public double withdrawAmount(int id, double amount) throws AccountNotFoundException, InvalidAmountException, LowBalanceException {

		Account account = accountDao.findById(id).orElse(null);

		if (account == null)
			throw new AccountNotFoundException("Account is not present in database");

		if(account.getBalance() < amount)
			throw new LowBalanceException("Insufficient Funds");
		
		if (amount < 0)
			throw new InvalidAmountException("Invalid amount entered");

		Transaction transaction = new Transaction();

		transaction.setAccount(account);
		transaction.setAmount(amount);
		transaction.setBalance(account.getBalance() - amount);
		transaction.setTransactionType(TransactionType.WITHDRAW);

		account.setBalance(account.getBalance() - amount);
		account.addTransaction(transaction);

		accountDao.save(account);
		return account.getBalance();
	}

	@Override
	public List<Transaction> showLastTransactions(int id) throws AccountNotFoundException {
		Account account = accountDao.findById(id).orElse(null);
		if (account == null)
			throw new AccountNotFoundException("Account does not exist");
		return transcationDao.findAllByAccountNumber(id);
	}

	@Override
	@Transactional
	public double fundTransfer(int senderAccountNumber, int recieverAccountNumber, double amount)
			throws AccountNotFoundException, LowBalanceException, SameAccountException, InvalidAmountException {

		Account senderAccount = accountDao.findById(senderAccountNumber).orElse(null);
		Account recieverAccount = accountDao.findById(recieverAccountNumber).orElse(null);
		

		if (senderAccount == null)
			throw new AccountNotFoundException("Sender account not found");

		if (recieverAccount == null)
			throw new AccountNotFoundException("Reciever account not found");

		if(senderAccountNumber == recieverAccountNumber)
			throw new SameAccountException("Both Accounts are the same");
		
		if (amount > senderAccount.getBalance())
			throw new LowBalanceException("Insufficient funds, balance = " + senderAccount.getBalance());

		if (amount < 0)
			throw new InvalidAmountException("Invalid amount entered");

		senderAccount.setBalance(senderAccount.getBalance() - amount);
		recieverAccount.setBalance(recieverAccount.getBalance() + amount);

		Transaction senderTransaction = new Transaction();
		senderTransaction.setAccount(senderAccount);
		senderTransaction.setAmount(amount);
		senderTransaction.setBalance(senderAccount.getBalance());
		senderTransaction.setTransactionType(TransactionType.FUND_TRANSFER);
		senderAccount.addTransaction(senderTransaction);
		accountDao.save(senderAccount);

		Transaction recieverTransaction = new Transaction();
		recieverTransaction.setAccount(senderAccount);
		recieverTransaction.setAmount(amount);
		recieverTransaction.setBalance(recieverAccount.getBalance());
		recieverTransaction.setTransactionType(TransactionType.DEPOSIT);
		recieverAccount.addTransaction(recieverTransaction);
		accountDao.save(recieverAccount);

		return senderAccount.getBalance();
	}

}
