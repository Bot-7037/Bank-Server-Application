package com.cg.bankapp.beans;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Bean for transaction to hold information about transaction type, the amount
 * processed and the date of transaction
 * 
 * @author himanegi
 *
 */
@Entity
@Table(name = "transaction")
@NamedQuery(name = "getTransactions", query = "SELECT t from Transaction as t where t.account.accountNumber = :accountNumber")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;

	@ManyToOne
	@JoinColumn(name = "accountNumber", referencedColumnName = "accountNumber")
	private Account account;

	private int amount;
	private int balance;

	@CreationTimestamp
	private LocalDateTime transactionDate;
	private TransactionType transactionType;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TransactionType getTransactionType() {
		return transactionType;
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

	public LocalDateTime getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "|transactionType=" + transactionType + "\t amount=" + amount + "\t\tbalance= " + balance + "\t"
				+ getTransactionDate() + "\t|";
	}

}
