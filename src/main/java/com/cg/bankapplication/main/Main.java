package com.cg.bankapplication.main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cg.bankapplication.beans.Transaction;
import com.cg.bankapplication.services.BankAppServices;
import com.cg.bankapplication.services.BankAppServicesImpl;

/**
 * Driver class
 * 
 * @author himanegi
 * 
 */
public class Main {

	// Services object to access the services implemented in BankAppServicesImpl
	static BankAppServices services;

	static Scanner inScanner;

	public static void main(String[] args) {
		try {
			Class.forName("com.cg.bankapplication.util.Database");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not load database");
			System.exit(0);
		}
		services = new BankAppServicesImpl();

		inScanner = new Scanner(System.in);

		// Variable to get user choice amongst 6 possible inputs
		int choice;

		// Keep looping till user wants to exit
		do {
			printMenu();

			choice = inScanner.nextInt();
			switch (choice) {

			case 1: {
				showBalance();
				break;
			}

			case 2: {
				deposit();
				break;
			}

			case 3: {
				withdraw();
				break;
			}

			case 4: {
				fundTransfer();
				break;
			}

			case 5: {
				showTransactions();
				break;
			}

			// Exit the program
			case 6: {
				System.out.println("\n~ Program Ended ~");
				break;
			}

			default:
				System.err.println("Invlaid Choice");
			}
		} while (choice != 6);

		inScanner.close();
	}

	static void printMenu() {
		System.out.println("\n------------------------------\n");
		System.out.println(" Enter choice\n");
		System.out.println(" 1. Show Balance");
		System.out.println(" 2. Deposit");
		System.out.println(" 3. Withdraw");
		System.out.println(" 4. Fund Transfer");
		System.out.println(" 5. Show last transactions");
		System.out.println(" 6. Exit\n");
		System.out.println("\n------------------------------\n");

		System.out.print("-> ");
	}

	/**
	 * Function to take input from user to invoke showBalance method from services
	 */
	static void showBalance() {
		System.out.print("\nEnter account number: ");
		try {
			int accountNumber = inScanner.nextInt();
			int balance = services.showBalance(accountNumber);
			System.out.print("\nAccount balance: " + balance);
		} catch (InputMismatchException e) {
			System.err.println("Invalid input");
			inScanner.next();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Function to take input from user to invoke deposit method from services
	 */
	static void deposit() {
		try {
			System.out.print("\nEnter account number: ");
			int account = inScanner.nextInt();
			System.out.print("\nEnter amount to deposit: ");
			int amount = inScanner.nextInt();
			Transaction transaction = services.deposit(account, amount);
			System.out.println("\nDeposited " + transaction.getAmount());
		} catch (InputMismatchException e) {
			System.err.println("Invalid input");
			inScanner.next();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Function to take input from user to invoke withdraw method from services
	 */
	static void withdraw() {
		try {
			System.out.print("\nEnter account number: ");
			int account = inScanner.nextInt();
			System.out.print("\nEnter amount to withdraw: ");
			int amount = inScanner.nextInt();
			Transaction transaction = services.withdraw(account, amount);
			System.out.println("\nWithdrew " + transaction.getAmount());
		} catch (InputMismatchException e) {
			System.err.println("Invalid input");
			inScanner.next();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Function to take input from user to invoke fundTransfer method from services
	 */
	static void fundTransfer() {
		System.out.print("\nEnter source account number: ");
		try {
			int account1 = inScanner.nextInt();
			System.out.print("\nEnter destination account: ");
			int account2 = inScanner.nextInt();
			System.out.print("\nEnter amount to transfer: ");
			int amount = inScanner.nextInt();
			services.fundTransfer(account1, account2, amount);
			System.out.println("\nTransferred");
		} catch (InputMismatchException e) {
			System.err.println("Invalid input");
			inScanner.next();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Function to take input from user to invoke showTransactions method from
	 * services
	 */
	static void showTransactions() {
		try {
			System.out.print("\nEnter account number: ");
			int accountNumber = inScanner.nextInt();
			List<Transaction> transaction = services.showTransactions(accountNumber);
			for (Transaction transaction2 : transaction) {
				if (transaction2 != null)
					System.out.println(transaction2);
			}
		} catch (InputMismatchException e) {
			System.err.println("Invalid input");
			inScanner.next();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
