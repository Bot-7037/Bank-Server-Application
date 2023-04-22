package com.cg.bankapp.dao;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.utils.Database;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;

import java.util.List;
import java.util.Optional;

public class BankAppDAOImpl implements BankAppDAO {
	EntityManager entityManager = null;

	public BankAppDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
		new Database();
	}

	@Override
	public boolean saveAccount(Account account) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(account);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Account getAccountById(int accountNumber) throws AccountNotFoundException {
		Account account;
		try {
			account = entityManager.find(Account.class, accountNumber);

			if (account == null)
				throw new AccountNotFoundException("Invalid Account Number");

			TypedQuery<Transaction> query = entityManager.createNamedQuery("getTransactions", Transaction.class);
			query.setParameter("accountNumber", accountNumber);
			List<Transaction> transactions = query.getResultList();
			account.setTransaction(transactions);
			return account;
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override 
	public boolean addTransactionToDatabase(Transaction transaction, Transaction transaction2)
			throws AccountNotFoundException {
		try {
			entityManager.getTransaction().begin();
			Account fromAccount = entityManager.find(Account.class, transaction.getAccount().getAccountNumber());
			if (fromAccount == null)
				throw new AccountNotFoundException("Invalid Acccount");
			fromAccount.setBalance(transaction.getBalance());
			entityManager.persist(transaction);

			Optional<Transaction> transactionChecker = Optional.ofNullable(transaction2);
			if (transactionChecker.isPresent()) {
				Account toAccount = entityManager.find(Account.class, transaction2.getAccount().getAccountNumber());
				if (toAccount == null)
					throw new AccountNotFoundException("Invalid Account");
				toAccount.setBalance(transaction2.getBalance());
				entityManager.persist(transaction2);
			}

			entityManager.getTransaction().commit();
			return true;
		} catch (AccountNotFoundException e) {
			if (entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw e;
		}
	}
}
