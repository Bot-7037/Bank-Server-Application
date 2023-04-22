package com.cg.bankapp.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class to create a database connection and return to caller
 * 
 * @author himanegi
 *
 */
public class DatabaseConnection {

	private DatabaseConnection() {
		throw new IllegalStateException("Utility class");
	}

	public static Connection getConnection() {
		Connection connection = null;
		Properties properties = new Properties();
		try (FileReader fileReader = new FileReader("Config.properties");) {
			properties.load(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(properties.getProperty("mysqlJDBCDriver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		try {
			connection = DriverManager.getConnection(properties.getProperty("jdbcurl"),
					properties.getProperty("username"), properties.getProperty("password"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return connection;
	}
}
