package com.nlogax.banking.service;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.AccountRepository;
import com.nlogax.banking.web.dto.AccountDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountService  {
    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account save(AccountDto accountDto) {
        Long id = Long.parseLong(accountDto.getId());
        Account account = null;

        if (repository.existsById(id)) {
            account = repository.getOne(id);
            account.setName(accountDto.getName());
            account.setCurrency(accountDto.getCurrency());
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();

            account = new Account(user, accountDto.getName(), accountDto.getCurrency());
            account.setBalance(100);
            user.getAccounts().add(account);
        }

        repository.save(account);

        return account;
    }
}
