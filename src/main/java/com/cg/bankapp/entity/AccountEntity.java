package com.cg.bankapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// Account Entity class to store and persist account details
@Entity
@Table(name = "account")
public class AccountEntity {
	// Primary key accountNumber (autoIncrement)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountNumber;
	private int balance;

	@OneToOne
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	private CustomerEntity customer;

	@OneToMany(mappedBy = "transactionId", cascade = CascadeType.ALL)
	private List<TransactionEntity> transactions;

	public AccountEntity() {
	}

	public AccountEntity(int accountNumber, int balance, CustomerEntity customer) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customer = customer;
		this.transactions = new ArrayList<>();
	}

	void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public List<TransactionEntity> getTransactions() {
		return transactions;
	}

	public void addTransaction(TransactionEntity transaction) {
		this.transactions.add(transaction);
		transaction.setAccount(this);
	}

	public void removeTransaction(TransactionEntity transaction) {
		this.transactions.remove(transaction);
		transaction.setAccount(null);
	}

	public void setTransaction(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

}
