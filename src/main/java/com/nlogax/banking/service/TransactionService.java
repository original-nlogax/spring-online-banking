package com.nlogax.banking.service;

import com.nlogax.banking.dto.TransactionDto;
import com.nlogax.banking.exception.AlreadyExistsException;
import com.nlogax.banking.exception.NotEnoughMoneyException;
import com.nlogax.banking.exception.TransactionDoesntExistException;
import com.nlogax.banking.exception.UserUnauthorizedException;
import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.Transaction;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    public boolean exists (Long id) {
        return repository.existsById(id);
    }

    public Transaction get (Long id) {
        Optional<Transaction> account = repository.findById(id);
        if (account.isEmpty())
            throw new TransactionDoesntExistException();

        return account.get();
    }

    public List<Transaction> getAccountTransactions (String number) {
        User authUser = sessionService.getAuthUser();
        boolean authority = authUser.isAdmin() || accountService.getByNumber(number).getUser().getId().equals(authUser.getId());
        if (!authority)
            throw new UserUnauthorizedException("Account doesn't belong to logged user");

        List<Transaction> transactions = repository.numberFrom(number);
        transactions.addAll(repository.numberTo(number));
        transactions.sort(Comparator.comparing(Transaction::getDate));
        return transactions;
    }

    // get transactions from all user accounts, removing duplicates by using HashSet
    public List<Transaction> getUserTransactions (User user) {
        User authUser = sessionService.getAuthUser();

        boolean authority = authUser.isAdmin() || user.getEmail().equals(authUser.getEmail());
        if (!authority)
            throw new UserUnauthorizedException("Account doesn't belong to logged user");

        HashSet<Transaction> transactions = new HashSet<>();
        user.getAccounts().forEach(account -> transactions.addAll(getAccountTransactions(account.getNumber())));
        return transactions.stream().toList();
    }

    public Transaction save (TransactionDto transactionDto) {
        if (transactionDto.getId() != null) {
            throw new AlreadyExistsException();
        }
        
        Account origin = accountService.getByNumber(transactionDto.getNumberFrom());
        Account destination = accountService.getByNumber(transactionDto.getNumberTo());

        if (origin.getBalance().compareTo(transactionDto.getAmount()) < 0) {
            throw new NotEnoughMoneyException();
        }

        Transaction transaction = new Transaction(
                ZonedDateTime.now(), origin.getNumber(), destination.getNumber(),
                transactionDto.getAmount(), origin.getCurrency()
        );

        origin.withdraw(transaction.getAmount());
        destination.deposit(transaction.getAmount());
        repository.save(transaction);
        return transaction;
    }

}
