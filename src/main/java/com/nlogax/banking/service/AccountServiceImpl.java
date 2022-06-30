package com.nlogax.banking.service;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.Role;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.AccountRepository;
import com.nlogax.banking.repository.UserRepository;
import com.nlogax.banking.utils.Utils;
import com.nlogax.banking.web.dto.AccountDto;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
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
