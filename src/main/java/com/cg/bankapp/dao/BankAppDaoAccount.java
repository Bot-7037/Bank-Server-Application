package com.cg.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bankapp.beans.Account;

/**
 * DAO interface to access account from the database
 * 
 * @author himanegi
 *
 */
public interface BankAppDaoAccount extends JpaRepository<Account, Integer> {
}