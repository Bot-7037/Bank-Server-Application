package com.cg.bankapplication.beans;

/**
 * Bean to hold customer information i.e. customer id and name
 * 
 * @author himanegi
 *
 */
public class Customer {
	int customerID;
	String customerName;

	public Customer() {
		this.customerID = 0;
		this.customerName = "";
	}

	public Customer(int id, String customerName) {
		this.customerID = id;
		this.customerName = customerName;
	}

	int getCustomerID() {
		return customerID;
	}

	void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	String getCustomerName() {
		return customerName;
	}

	void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	

}
