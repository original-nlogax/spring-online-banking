package com.nlogax.banking.service;

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

    public boolean exists (Long id) {
        return repository.existsById(id);
    }

    // todo should be accessible only for admins
    // maybe attribute based authority check?
    public Account get (Long id) {
        Optional<Account> account = repository.findById(id);
        return account.orElse(null);
    }

    public Account save (AccountDto accountDto) {
        if (accountDto.getId() != null) {
            // if accountDto has id, then it means that it is already saved in db
            return null;
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

    public boolean update (AccountDto accountDto) {
        if (accountDto.getId() == null) {
            // if accountDto has no id, then it means that it is absent from db
            return false;
        }

        Long id = Long.parseLong(accountDto.getId());
        Optional<Account> account = repository.findById(id);
        if (account.isPresent()) {
            account.get().setName(accountDto.getName());
            account.get().setCurrency(accountDto.getCurrency());

            repository.save(account.get());
        }

        return account.isPresent();
    }

    public boolean delete (Long id) {
        Optional<Account> account = repository.findById(id);
        if (account.isPresent()) {
            User accountHolder = account.get().getUser();
            accountHolder.getAccounts().remove(account.get());
            repository.delete(account.get());
        }

        return account.isPresent();
    }
}
