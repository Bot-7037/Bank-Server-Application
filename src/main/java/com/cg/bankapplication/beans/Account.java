package com.cg.bankapplication.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean for an account, that stores information about account, transaction and
 * customer along with last 10 transactions
 * 
 * @author himanegi
 *
 */
public class Account {
	int accountNumber;
	int balance;
	int transactionNumber;
	Customer customer;
	ArrayList<Transaction> transactions;

	public Account(int accountNumber, int balance, Customer customer) {
		/**
		 * Parametrized constructor to create a new account with initial properties
		 * 
		 * @param accountNumber: account number
		 * @param balance:       initial balance of the account
		 * @param customer:      customer related to the account
		 */
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customer = customer;
		this.transactions = new ArrayList<>();
		this.transactionNumber = 0;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void addTransaction(Transaction transaction) {
		transactions.add(0, transaction);
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", balance=" + balance + ", transactionNumber="
				+ transactionNumber + ", customer=" + customer + ", transactions=" + transactions + "]";
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

}
