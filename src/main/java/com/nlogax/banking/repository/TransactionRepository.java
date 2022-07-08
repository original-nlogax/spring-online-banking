package com.nlogax.banking.repository;

import com.nlogax.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // fixme design flaw? calling to accounts from transaction repository
    // hash maybe?
    List<Transaction> numberTo (String numberTo);
    List<Transaction> numberFrom (String numberFrom);
}