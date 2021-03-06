package com.nlogax.banking.service;

import com.nlogax.banking.dto.AccountDto;
import com.nlogax.banking.exception.AccountDoesntExistException;
import com.nlogax.banking.exception.AlreadyExistsException;
import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService  {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private SessionService sessionService;

    public boolean existsById (Long id) {
        return repository.existsById(id);
    }

    public boolean existsByNumber (String number) {
        return getByNumber(number) != null;
    }

    public Account getByNumber (String number) {
        Optional<Account> account = repository.getByNumber(number);
        if (account.isEmpty())
            throw new AccountDoesntExistException();

        return account.get();
    }

    public Account get (Long id) {
        Optional<Account> account = repository.findById(id);
        if (account.isEmpty())
            throw new AccountDoesntExistException();

        return account.get();
    }

    public Account save (AccountDto accountDto) {
        if (accountDto.getId() != null) {
            // if accountDto has id, then it means that it is already saved in db
            throw new AlreadyExistsException();
        }

        User user = sessionService.getAuthUser();

        Account account;
        account = new Account(user, accountDto.getName(), accountDto.getCurrency());
        account.setBalance(BigDecimal.ZERO);
        user.getAccounts().add(account);

        repository.save(account);
        return account;
    }

    public void update (Account account, AccountDto accountDto) {
        /*
        if (accountDto.getId() == null) {
            // if accountDto has no id, then it means that it is absent from db
            throw new AccountDoesntExistException();
        }

        Long id = Long.parseLong(accountDto.getId());
        Optional<Account> account = repository.findById(id);
        if (account.isEmpty())
            throw new AccountDoesntExistException();*/

        account.setName(accountDto.getName());
        account.setCurrency(accountDto.getCurrency());
        repository.save(account);
    }

    public void delete (Long id) {
        Optional<Account> account = repository.findById(id);
        if (account.isEmpty())
            throw new AccountDoesntExistException();

        User accountHolder = account.get().getUser();
        accountHolder.getAccounts().remove(account.get());
        repository.delete(account.get());
    }
}
