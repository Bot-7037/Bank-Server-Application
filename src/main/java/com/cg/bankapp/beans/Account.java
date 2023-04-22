package com.cg.bankapp.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Account {
	// Primary key accountNumber (autoIncrement)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountNumber;
	private double balance;

	@OneToOne
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	private Customer customer;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "transactionId", cascade = CascadeType.ALL)
	private List<Transaction> transactions;

	public Account() {
	}

	public Account(int accountNumber, double balance, Customer customer) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customer = customer;
		this.transactions = new ArrayList<>();
	}

	void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
		transaction.setAccount(this);
	}

	public void removeTransaction(Transaction transaction) {
		this.transactions.remove(transaction);
		transaction.setAccount(null);
	}

	public void setTransaction(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return String.format("Account [accountNumber=%s, balance=%s, customer=%s, transactions=%s]", accountNumber,
				balance, customer, transactions);
	}

}
