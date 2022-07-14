package com.nlogax.banking.controller.api;

import com.nlogax.banking.dto.TransactionDto;
import com.nlogax.banking.model.Transaction;
import com.nlogax.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService service;

    // get all transactions of a single account
    @GetMapping(value="/account/{number}")
    public ResponseEntity<List<Transaction>> getAccountTransactions (@PathVariable String number) {
        List<Transaction> transactions = service.getAccountTransactions(number);
        return ok(transactions);
    }

    // get all transactions of a logged user accounts
    @GetMapping(value="/user")
    public ResponseEntity<List<Transaction>> getUserTransactions () {
        List<Transaction> transactions = service.getUserTransactions();
        return ok(transactions);
    }

    @GetMapping(value = "/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Transaction> get (@PathVariable Long id) {
        Transaction transaction = service.get(id);
        return ok(transaction);
    }

    @PostMapping
    public ResponseEntity<Void> add (@ModelAttribute("transactionDto") TransactionDto data) {
        service.save(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
