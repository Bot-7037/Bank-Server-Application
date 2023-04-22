package com.cg.bankapp.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

/**
 * Utility class to initialize the database and clean up previous operations for
 * dev build
 * 
 * @author himanegi
 *
 */
public class Database {

	public Database() {
		// cleanup
		cleanup();

		// create database
		String queryPath = "C:\\Users\\HIMANEGI\\eclipse-workspace\\BankApplicationJDBC\\Queries.sql";
		try (Connection connection = DatabaseConnection.getConnection();
				Reader reader = new BufferedReader(new FileReader(queryPath));) {
			ScriptRunner scriptRunner = new ScriptRunner(connection);
			scriptRunner.setLogWriter(null);
			scriptRunner.runScript(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cleanup() {
		// cleanup previous action for dev-version
		// delete transactions table
		String query = "DROP TABLE transaction;";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// delete accounts table
		query = "DROP TABLE account;";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// delete customers table
		query = "DROP TABLE customer;";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
