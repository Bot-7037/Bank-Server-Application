package com.cg.bankapp.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.bankapp.entity.AccountEntity;
import com.cg.bankapp.entity.TransactionEntity;
import com.cg.bankapp.exceptions.AccountNotFoundException;

@Repository
public class BankAppDAOImpl implements BankAppDAO {

	@PersistenceContext
	private EntityManager entityManager;

	protected BankAppDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public BankAppDAOImpl() {
	}

	@Override
	public Integer saveAccount(AccountEntity account) {
		Integer accoutNumber;
		entityManager.persist(account);
		accoutNumber = account.getAccountNumber();
		return accoutNumber;
	}

	@Override
	public AccountEntity getAccountById(Integer accountNumber) throws AccountNotFoundException {
		AccountEntity accountEntity = entityManager.find(AccountEntity.class, accountNumber);

		if (accountEntity != null) {
			TypedQuery<TransactionEntity> query = entityManager.createNamedQuery("getTransactions",
					TransactionEntity.class);
			query.setParameter("accountNumber", accountNumber);
			List<TransactionEntity> transactions = query.getResultList();
			accountEntity.setTransaction(transactions);
		} else {
			throw new AccountNotFoundException("Account Not Found");
		}
		return accountEntity;
	}

	@Override
	public boolean addTransactionToDatabase(TransactionEntity transaction, TransactionEntity transaction2)
			throws AccountNotFoundException {
		AccountEntity fromAccount = entityManager.find(AccountEntity.class,
				transaction.getAccount().getAccountNumber());
		if (fromAccount == null)
			throw new AccountNotFoundException("Invalid Acccount");
		fromAccount.setBalance(transaction.getBalance());
		entityManager.persist(transaction);

		Optional<TransactionEntity> transactionChecker = Optional.ofNullable(transaction2);
		if (transactionChecker.isPresent()) {
			AccountEntity toAccount = entityManager.find(AccountEntity.class,
					transaction2.getAccount().getAccountNumber());
			if (toAccount == null)
				throw new AccountNotFoundException("Invalid Account");
			toAccount.setBalance(transaction2.getBalance());
			entityManager.persist(transaction2);
		}

		return true;
	}

}
