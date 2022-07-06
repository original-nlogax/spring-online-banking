package com.nlogax.banking.controller.api;

import com.nlogax.banking.model.Transaction;
import com.nlogax.banking.service.TransactionService;
import com.nlogax.banking.web.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Transaction> get (@PathVariable Long id) {
        Transaction transaction = service.get(id);
        if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // or notFound().build() ?
        else return ok(transaction);
    }

    @PostMapping
    public ResponseEntity<Void> add (@ModelAttribute("transactionDto") TransactionDto data) {
        // exceptions are thrown in service layer
        service.save(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
