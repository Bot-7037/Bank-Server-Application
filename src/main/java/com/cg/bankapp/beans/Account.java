package com.cg.bankapp.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Bean to store Accounts data
 * 
 * @author himanegi
 *
 */

@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue
	private int accountNumber;
	private int balance;

	@OneToOne
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	private Customer customer;

	@OneToMany(mappedBy = "transactionId", cascade = CascadeType.ALL)
	private List<Transaction> transactions;

	public Account() {
	}

	public Account(int accountNumber, int balance, Customer customer) {
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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
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

}
