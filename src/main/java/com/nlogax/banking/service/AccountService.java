package com.nlogax.banking.service;

import com.nlogax.banking.exception.AccountDoesntExistException;
import com.nlogax.banking.exception.AlreadyExistsException;
import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.AccountRepository;
import com.nlogax.banking.web.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService  {

    @Autowired
    private AccountRepository repository;

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        Account account;
        account = new Account(user, accountDto.getName(), accountDto.getCurrency());
        account.setBalance(0);
        user.getAccounts().add(account);

        repository.save(account);
        return account;
    }

    public void update (AccountDto accountDto) {
        if (accountDto.getId() == null) {
            // if accountDto has no id, then it means that it is absent from db
            throw new AccountDoesntExistException();
        }

        Long id = Long.parseLong(accountDto.getId());
        Optional<Account> account = repository.findById(id);
        if (account.isEmpty())
            throw new AccountDoesntExistException();

        account.get().setName(accountDto.getName());
        account.get().setCurrency(accountDto.getCurrency());
        repository.save(account.get());
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
