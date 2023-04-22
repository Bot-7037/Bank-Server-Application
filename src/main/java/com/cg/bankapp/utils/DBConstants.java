package com.cg.bankapp.utils;

public class DBConstants {
	private DBConstants() {
		throw new IllegalStateException();
	}

	public static final String INSERT_INTO_CUSTOMER_QUERY = "INSERT INTO customer(customerId, customerName) VALUES(?,?);";
	public static final String INSERT_INTO_ACCOUNT_QUERY = "INSERT INTO account(accountNumber, customerId, balance) VALUES(?,?,?);";
	public static final String INSERT_INTO_TRANSACTION_QUERY = "INSERT INTO transaction(amount, finalBalance, fromAccount, toAccount) values(?, ?, ?, ?);";
	public static final String GET_FROM_ACCOUNT_TABLE = "SELECT * FROM account WHERE accountNumber = ?;";
	public static final String GET_FROM_CUSTOMER_TABLE = "SELECT * FROM customer WHERE customerId = ?;";
	public static final String GET_FROM_TRANSACTION_TABLE = "SELECT * FROM transaction WHERE fromAccount=? or toAccount=? limit 10;";
	public static final String UPDATE_ACCOUNT_QUERY = "UPDATE account SET balance = ? WHERE accountNumber = ?;";
}
