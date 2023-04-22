
package com.cg.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import com.cg.bankapp.utils.DBConstants;
import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Customer;
import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.beans.TransactionType;
import com.cg.bankapp.exceptions.AccountNotFoundException;

public class BankAppDAOImpl implements BankAppDAO {

	private Connection connection = null;

	public BankAppDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean saveAccount(Account account) {
		Savepoint savepoint;
		try {
			savepoint = connection.setSavepoint();
		} catch (SQLException e1) {
			e1.printStackTrace();
			savepoint = null;
			return false;
		}

		// insert into customer
		try (PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.INSERT_INTO_CUSTOMER_QUERY);) {
			preparedStatement.setInt(1, account.getCustomer().getCustomerId());
			preparedStatement.setString(2, account.getCustomer().getCustomerName());
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Unable to save customer");
			return false;
		}

		// insert customer into account
		try (PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.INSERT_INTO_ACCOUNT_QUERY);) {
			preparedStatement.setInt(1, account.getAccountNumber());
			preparedStatement.setInt(2, account.getCustomer().getCustomerId());
			preparedStatement.setInt(3, account.getBalance());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			try {
				connection.rollback(savepoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public Account getAccountById(int accountNumber) throws AccountNotFoundException {
		Account account = new Account();
		int customerId = 1;

		// get from account table

		try (PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_FROM_ACCOUNT_TABLE)) {
			preparedStatement.setInt(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst())
				throw new AccountNotFoundException("Invalid Account");
			account.setAccountNumber(accountNumber);
			while (resultSet.next()) {
				account.setBalance(resultSet.getInt("balance"));
				customerId = resultSet.getInt("customerId");
			}
		} catch (AccountNotFoundException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// get from customer table
		try (PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_FROM_CUSTOMER_TABLE)) {
			preparedStatement.setInt(1, customerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				account.setCustomer(new Customer(resultSet.getInt("customerId"), resultSet.getString("customerName")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// get from transaction table
		List<Transaction> transactionsList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_FROM_TRANSACTION_TABLE)) {
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.setInt(2, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Transaction transaction = new Transaction();
				transaction.setAmount(resultSet.getInt("amount"));
				transaction.setBalance(resultSet.getInt("finalBalance"));
				transaction.setTransactionDate(resultSet.getTimestamp("transactionDate"));
				transaction.setFromAccount(resultSet.getInt("fromAccount"));
				transaction.setToAccount(resultSet.getInt("toAccount"));
				if (transaction.getFromAccount() == -1)
					transaction.setTransactionType(TransactionType.DEPOSIT);
				else if (transaction.getToAccount() == -1)
					transaction.setTransactionType(TransactionType.WITHDRAW);
				else
					transaction.setTransactionType(TransactionType.FUND_TRANSFER);
				transactionsList.add(transaction);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		account.setTransaction(transactionsList);
		return account;
	}

	@Override
	public boolean updateAccount(int accountNumber, int balance) {
		try (PreparedStatement statement = connection.prepareStatement(DBConstants.UPDATE_ACCOUNT_QUERY)) {
			statement.setInt(1, balance);
			statement.setInt(2, accountNumber);
			return statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addTransaction(Transaction transaction) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.INSERT_INTO_TRANSACTION_QUERY)) {
			preparedStatement.setInt(1, transaction.getAmount());
			preparedStatement.setInt(2, transaction.getBalance());
			preparedStatement.setInt(3, transaction.getFromAccount());
			preparedStatement.setInt(4, transaction.getToAccount());
			return preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
