package com.cg.bankapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.bankapp.beans.Transaction;

/**
 * DAO interface to access transactions from database
 * 
 * @author himanegi
 *
 */
public interface BankAppDaoTransaction extends JpaRepository<Transaction, Integer> {

	@Query(value = "SELECT * FROM transaction WHERE account_number = ?;", nativeQuery = true)
	List<Transaction> findAllByAccountNumber(int accountNumber);

}
