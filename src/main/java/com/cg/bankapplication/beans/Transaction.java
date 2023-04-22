package com.cg.bankapplication.beans;

/**
 * Bean for transaction to hold information about transaction type, the amount
 * processed and the date of transaction
 * 
 * @author himanegi
 *
 */
public class Transaction {
	TransactionType transactionType;
	int amount;
	String dateString;
	int balance;

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

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public int getAmount() {
		return this.amount;
	}

	@Override
	public String toString() {
		if (transactionType == TransactionType.WITHDRAW)
			return "transactionType=" + transactionType + "\t amount=" + amount + "\t dateString=" + dateString
					+ "\t\tbalance= " + balance;
		return "transactionType=" + transactionType + "\t\t amount=" + amount + "\t dateString=" + dateString
				+ "\t\tbalance= " + balance;
	}


}
