package com.cg.bankapp.utils;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.cg.bankapp.beans.Account;
import com.cg.bankapp.beans.Customer;

public class Database {
	private List<String> customerNames = Arrays.asList("Himanshu", "Vivek", "Garvit", "Ashish", "Basudev", "Shivam",
			"Rohit", "Mahi", "Suresh", "Raunak");

	public Database() {
		EntityManager entityManager = EntityManagerGenerator.getEntityManager();

		entityManager.getTransaction().begin();

		for (String customerName : customerNames) {
			Customer customer = new Customer();
			customer.setCustomerName(customerName);

			Account account = new Account();
			account.setBalance(0);
			account.setCustomer(customer);

			entityManager.persist(customer);
			entityManager.persist(account);
		}

		entityManager.getTransaction().commit();
	}
}