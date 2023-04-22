package com.cg.bankapp.main;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.utils.MenuConstants;
import com.cg.bankapp.services.BankAppServicesImpl;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.AccountNotFoundException;

/**
 * Driver class
 * 
 * @author himanegi
 * 
 */
public class Main {

	// Services object to access the services implemented in BankAppServicesImpl
	static BankAppServicesImpl services;

	static Scanner scanner;

	public static void main(String[] args) {

		services = new BankAppServicesImpl();

		scanner = new Scanner(System.in);

		// Variable to get user choice amongst 6 possible inputs
		int choice;

		// Keep looping till user wants to exit
		do {
			printMenu();
			try {
				choice = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Invalid Input");
				choice = -1;
				scanner.next();
				continue;
			}

			switch (choice) {

			case 1:
				showBalance();
				break;

			case 2:
				deposit();
				break;

			case 3:
				withdraw();
				break;

			case 4:
				fundTransfer();
				break;

			case 5:
				showTransactions();
				break;

			// Exit the program
			case 6:
				System.out.println("\n~ Program Ended ~");
				break;

			default:
				System.err.println("Invalid Choice");
			}
		} while (choice != 6);

		scanner.close();
	}

	static void printMenu() {
		System.out.print(MenuConstants.menu);
	}

	/**
	 * Function to take input from user to invoke showBalance method from services
	 */
	static void showBalance() {
		int accountNumber;
		System.out.print("\nEnter account number: ");
		try {
			accountNumber = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("Invalid Input");
			scanner.next();
			return;
		}
		try {
			int balance = services.showBalance(accountNumber);
			System.out.print("\nAccount balance: " + balance);
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
			int account = scanner.nextInt();

			System.out.print("\nEnter amount to deposit: ");
			int amount = scanner.nextInt();
			Transaction transaction = services.deposit(account, amount);
			System.out.println("\nDeposited " + transaction.getAmount());
		} catch (InputMismatchException e) {
			System.err.println("Invalid Input");
			scanner.next();
		} catch (AccountNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to take input from user to invoke withdraw method from services
	 */
	static void withdraw() {
		try {
			System.out.print("\nEnter account number: ");
			int account = scanner.nextInt();
			System.out.print("\nEnter amount to withdraw: ");
			int amount = scanner.nextInt();
			Transaction transaction = services.withdraw(account, amount);
			System.out.println("\nWithdrew " + transaction.getAmount());
		} catch (InputMismatchException e) {
			System.err.println("Invalid Input");
			scanner.next();
		} catch (InvalidAmountException | AccountNotFoundException | LowBalanceException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to take input from user to invoke fundTransfer method from services
	 */
	static void fundTransfer() {
		int account1;
		int account2;
		int amount;
		try {
			System.out.print("\nEnter source account number: ");
			account1 = scanner.nextInt();
			System.out.print("\nEnter destination account: ");
			account2 = scanner.nextInt();
			System.out.print("\nEnter amount to transfer: ");
			amount = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("Invalid Input");
			scanner.next();
			return;
		}
		try {
			services.fundTransfer(account1, account2, amount);
			System.out.println("\nTransferred");
		} catch (InvalidAmountException | AccountNotFoundException | LowBalanceException | SameAccountException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to take input from user to invoke showTransactions method from
	 * services
	 */
	static void showTransactions() {
		try {
			System.out.print("\nEnter account number: ");
			int accountNumber = scanner.nextInt();
			List<Transaction> transactionList = services.showTransactions(accountNumber);
			for (Transaction transaction : transactionList) {
				if (transaction != null)
					System.out.println(transaction);
			}
		} catch (InputMismatchException e) {
			System.err.println("Invalid Input");
			scanner.next();
		} catch (AccountNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
