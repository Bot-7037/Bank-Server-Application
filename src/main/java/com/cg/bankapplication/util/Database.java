package com.cg.bankapplication.util;

import java.util.HashMap;
import java.util.Map;

import com.cg.bankapplication.beans.Account;
import com.cg.bankapplication.beans.Customer;

/**
 * Database, an array of objects that holds information for all accounts
 * 
 * @author himanegi
 *
 */
public class Database {
	public static Map<Integer, Account> accounts = new HashMap<>();

	/**
	 * The static information that would load up any time the app is run.
	 */
	static {
		Customer customer = new Customer(0, "Himanshu");
		Customer customer1 = new Customer(1, "Vivek");
		Customer customer2 = new Customer(2, "Ashu");
		Customer customer3 = new Customer(3, "Siddharth");
		Customer customer4 = new Customer(4, "Harshit");
		Customer customer5 = new Customer(5, "Harsh");
		Customer customer6 = new Customer(6, "Shubham");
		Customer customer7 = new Customer(7, "Kirti");
		Customer customer8 = new Customer(8, "Simran");
		Customer customer9 = new Customer(9, "Raj");

		accounts.putIfAbsent(0, new Account(0, 1000, customer1));
		accounts.putIfAbsent(1, new Account(1, 1000, customer2));
		accounts.putIfAbsent(2, new Account(2, 1000, customer3));
		accounts.putIfAbsent(3, new Account(3, 1000, customer4));
		accounts.putIfAbsent(4, new Account(4, 1000, customer5));
		accounts.putIfAbsent(5, new Account(5, 1000, customer6));
		accounts.putIfAbsent(6, new Account(6, 1000, customer7));
		accounts.putIfAbsent(7, new Account(7, 1000, customer8));
		accounts.putIfAbsent(8, new Account(8, 1000, customer9));
		accounts.putIfAbsent(9, new Account(9, 1000, customer));
	}
}
