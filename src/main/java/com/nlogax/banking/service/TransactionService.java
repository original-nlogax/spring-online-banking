package com.nlogax.banking.service;

import com.nlogax.banking.exception.AccountDoesntExistException;
import com.nlogax.banking.exception.AlreadyExistsException;
import com.nlogax.banking.exception.NotEnoughMoneyException;
import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.Transaction;
import com.nlogax.banking.repository.TransactionRepository;
import com.nlogax.banking.web.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AccountService accountService;

    public boolean exists (Long id) {
        return repository.existsById(id);
    }

    // todo should be accessible only for admins
    // maybe attribute based authority check?
    public Transaction get (Long id) {
        Optional<Transaction> account = repository.findById(id);
        return account.orElse(null);
    }

    public Transaction save (TransactionDto transactionDto) {
        if (transactionDto.getId() != null) {
            // if accountDto has id, then it means that it is already saved in db
            throw new AlreadyExistsException();
        }

        // suppose we can transfer money only between our bank accounts
        if (!accountService.existsByNumber(transactionDto.getNumberFrom()))
            throw new AccountDoesntExistException("Source account with number " + transactionDto.getNumberFrom() + " doesn't exist");

        if (!accountService.existsByNumber(transactionDto.getNumberTo()))
            throw new AccountDoesntExistException("Destination account with number " + transactionDto.getNumberTo() + " doesn't exist");


        Transaction transaction = new Transaction(
                transactionDto.getNumberFrom(), transactionDto.getNumberTo(), transactionDto.getAmount()
        );

        Account accountFrom = accountService.getByNumber(transactionDto.getNumberFrom());
        Account accountTo = accountService.getByNumber(transactionDto.getNumberTo());

        if (accountFrom.getBalance() < transactionDto.getAmount()) {
            throw new NotEnoughMoneyException();
        }

        accountFrom.withdraw(transaction.getAmount());
        accountTo.deposit(transaction.getAmount());

        repository.save(transaction);
        return transaction;
    }
}
