package com.cg.bankapp.main;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.InputMismatchException;

import com.cg.bankapp.utils.MenuConstants;
import com.cg.bankapp.service.BankAppServices;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.actionhandler.ActionHandler;
import com.cg.bankapp.entity.TransactionEntity;
import com.cg.bankapp.exceptions.AccountNotFoundException;

/**
 * Main driver code
 * 
 * @author himanegi
 *
 */
public class Main {

	static BankAppServices services;

	static Scanner scanner;

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("main-resources.xml");
		services = (BankAppServices) context.getBean("bankAppServicesImpl");

		scanner = new Scanner(System.in);

		System.out.println(MenuConstants.HEADING);

		int choice;

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
			case 6:
				System.out.println("\n~ Thank You ~");
				break;
			default:
				System.err.println("Invalid Choice");
			}
		} while (choice != 6);

		scanner.close();
	}

	static void printMenu() {
		System.out.print(MenuConstants.MENU);
	}

	static void showBalance() {
		int accountNumber;
		System.out.print("\nEnter account number: ");
		try {
			accountNumber = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("\nInvalid Input");
			scanner.next();
			return;
		}
		try {
			int balance = services.showBalance(accountNumber);
			System.out.println(MenuConstants.SHOWBALANCETOPBAR);
			System.out.println(" Account balance: " + balance + "\t\t");
			System.out.println(MenuConstants.SHOWBALANCEBOTTOMBAR);
		} catch (AccountNotFoundException e) {
			ActionHandler.handle(e);
		}
	}

	static void deposit() {
		try {
			System.out.print("\nEnter account number: ");
			int account = scanner.nextInt();

			System.out.print("\nEnter amount to deposit: ");
			int amount = scanner.nextInt();
			TransactionEntity transaction = services.deposit(account, amount);
			System.out.println(MenuConstants.DEPOSITTOPBAR);
			System.out.println(
					"  Deposited : " + transaction.getAmount() + "   Balance : " + transaction.getBalance() + "\t\t");
			System.out.println(MenuConstants.DEPOSITBOTTOMBAR);
		} catch (InputMismatchException e) {
			System.err.println("\nInvalid Input");
			scanner.next();
		} catch (AccountNotFoundException | InvalidAmountException e) {
			ActionHandler.handle(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void withdraw() {
		try {
			System.out.print("\nEnter account number: ");
			int account = scanner.nextInt();
			System.out.print("\nEnter amount to withdraw: ");
			int amount = scanner.nextInt();
			TransactionEntity transaction = services.withdraw(account, amount);
			System.out.println(MenuConstants.WITHDRAWTOPBAR);
			System.out.println(
					"  Withdrew : " + transaction.getAmount() + "   Balance : " + transaction.getBalance() + "\t\t\t");
			System.out.println(MenuConstants.WITHDRAWBOTTOMBAR);
		} catch (InputMismatchException e) {
			System.err.println("Invalid Input");
			scanner.next();
		} catch (InvalidAmountException | AccountNotFoundException | LowBalanceException e) {
			ActionHandler.handle(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void fundTransfer() {
		int fromAccount;
		int toAccount;
		int amount;
		try {
			System.out.print("\nEnter source account number: ");
			fromAccount = scanner.nextInt();
			System.out.print("\nEnter destination account: ");
			toAccount = scanner.nextInt();
			System.out.print("\nEnter amount to transfer: ");
			amount = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("\nInvalid Input");
			scanner.next();
			return;
		}
		try {
			TransactionEntity transaction = services.fundTransfer(fromAccount, toAccount, amount);
			System.out.println(MenuConstants.FUNDTRANSFERTOPBAR);
			System.out.println("  Transferred : " + transaction.getAmount() + " to account : " + toAccount
					+ "    Final Balance : " + transaction.getBalance() + "   \t");
			System.out.println(MenuConstants.FUNDTRANSFERBOTTOMBAR);
		} catch (InvalidAmountException | AccountNotFoundException | LowBalanceException | SameAccountException e) {
			ActionHandler.handle(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void showTransactions() {
		try {
			System.out.print("\nEnter account number: ");
			int accountNumber = scanner.nextInt();
			List<TransactionEntity> transactionList = services.showTransactions(accountNumber);
			System.out.println(MenuConstants.TRANSACTIONTOPBAR);
			for (TransactionEntity transaction : transactionList) {
				if (transaction != null)
					System.out.println(transaction);
			}

			System.out.println(MenuConstants.TRANSACTIONBOTTOMBAR);
		} catch (InputMismatchException e) {
			System.err.println("\nInvalid Input");
			scanner.next();
		} catch (AccountNotFoundException e) {
			ActionHandler.handle(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}