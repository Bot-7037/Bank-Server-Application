package com.cg.bankapp.controllers;

import static java.util.Map.entry;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bankapp.beans.Transaction;
import com.cg.bankapp.exceptions.AccountNotFoundException;
import com.cg.bankapp.exceptions.InvalidAmountException;
import com.cg.bankapp.exceptions.LowBalanceException;
import com.cg.bankapp.exceptions.SameAccountException;
import com.cg.bankapp.services.BankService;

/**
 * Rest Controller to handle the GET and POST requests from client
 * 
 * @author himanegi
 *
 */
@RestController
public class BankAppController {
	@Autowired
	private BankService service;

	/**
	 * test the application saying hello
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/welcome")
	public ResponseEntity<String> sayHello() {
		return new ResponseEntity<>("Welcome to bank server api", HttpStatus.OK);
	}

	/**
	 * get balance of a user from database
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping(path = "/showBalance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Double>> showBalance(@PathVariable int id) throws AccountNotFoundException {
		try {
			Double balance = service.showBalace(id);
			return new ResponseEntity<>(Collections.singletonMap("balance", balance), HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get last 10 transactions from the database
	 * 
	 * @param id
	 * @return ResponseEntity<Transaction>
	 * @throws AccountNotFoundException
	 */
	@GetMapping(path = "/showLastTransactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, Map<String, Object>>> showLastTransactions(@PathVariable int id)
			throws AccountNotFoundException {
		try {
			List<Transaction> transactions = service.showLastTransactions(id);

			Map<Integer, Map<String, Object>> allTransactions = new HashMap<>();

			for (Transaction transaction : transactions) {
				allTransactions.put(transaction.getTransactionId(),
						Map.ofEntries(entry("accountNumber", transaction.getAccount().getAccountNumber()),
								entry("transactionDate", transaction.getTransactionDate()),
								entry("amount", transaction.getAmount()), entry("balance", transaction.getBalance()),
								entry("transactionType", transaction.getTransactionType())));
			}
			return new ResponseEntity<>(allTransactions, HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Function to withdraw money from an account
	 * 
	 * @param input
	 * @return ResponseEntity<Balance>
	 * @throws Exception
	 */
	@PostMapping(value = "/withdrawMoney", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Double>> withdrawMoney(@RequestBody Map<String, String> input) throws Exception {
		int accountNumber = Integer.parseInt(input.get("accountNumber"));
		double amount = Double.parseDouble(input.get("amount"));
		double balance = 0.0;

		try {
			balance = service.withdrawAmount(accountNumber, amount);
			return new ResponseEntity<>(Collections.singletonMap("balance", balance), HttpStatus.OK);
		} catch (LowBalanceException | AccountNotFoundException | InvalidAmountException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Function to deposit money to an account
	 * 
	 * @param input
	 * @return ResponseEntity<Balance>
	 * @throws Exception 
	 */
	@PostMapping(value = "/depositMoney", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Double>> depositMoney(@RequestBody Map<String, String> input) throws Exception {
		int accountNumber = Integer.parseInt(input.get("accountNumber"));
		double amount = Integer.parseInt(input.get("amount"));
		Double balance;

		try {
			balance = service.depositAmount(accountNumber, amount);
			return new ResponseEntity<>(Collections.singletonMap("balance", balance), HttpStatus.OK);
		} catch (AccountNotFoundException | InvalidAmountException e) {
			throw e;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Function to transfer money to another account
	 * 
	 * @param input
	 * @return ResposeEntity<Balance>
	 * @throws Exception 
	 */
	@PostMapping(value = "/fundTransfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Double>> fundTransfer(@RequestBody Map<String, String> input) throws Exception {
		int recieverAccount = Integer.parseInt(input.get("recieverAccount"));
		int senderAccount = Integer.parseInt(input.get("senderAccount"));
		double amount = Integer.parseInt(input.get("amount"));
		double balance = 0.0;

		try {
			balance = service.fundTransfer(senderAccount, recieverAccount, amount);
			return new ResponseEntity<>(Collections.singletonMap("balance", balance), HttpStatus.OK);
		} catch (LowBalanceException | AccountNotFoundException | InvalidAmountException | SameAccountException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}