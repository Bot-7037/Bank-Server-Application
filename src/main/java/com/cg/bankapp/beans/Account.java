package com.cg.bankapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean to store Accounts data
 * 
 * @author himanegi
 *
 */
public class Account {
	private int accountNumber;
	private int balance;
	private Customer customer;
	private List<Transaction> transactions;

	public Account() {
		this.accountNumber = 0;
		this.balance = 0;
		this.customer = new Customer(0, "");
		this.transactions = new ArrayList<>();
	}

	public Account(int accountNumber, int balance, Customer customer) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customer = customer;
		this.transactions = new ArrayList<>();
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
	}

	public void setTransaction(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
