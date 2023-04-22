package com.cg.bankapp.beans;

import java.sql.Timestamp;

/**
 * Bean for transaction to hold information about transaction type, the amount
 * processed and the date of transaction
 * 
 * @author himanegi
 *
 */
public class Transaction {
	private TransactionType transactionType; // true for deposit, false for withdraw
	private int fromAccount;
	private int toAccount;
	private int amount;
	private int balance;
	private Timestamp transactionDate;

	public int getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(int fromAccount) {
		this.fromAccount = fromAccount;
	}

	public int getToAccount() {
		return toAccount;
	}

	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return this.amount;
	}

	@Override
	public String toString() {
		if (transactionType == TransactionType.WITHDRAW)
			return "transactionType=" + transactionType + "\t amount=" + amount + "\t\tbalance= " + balance + "\t"
					+ getTransactionDate();
		return "transactionType=" + transactionType + "\t\t amount=" + amount + "\t\tbalance= " + balance + "\t"
				+ getTransactionDate();
	}

	public Timestamp getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

}
