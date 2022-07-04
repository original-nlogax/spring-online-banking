package com.nlogax.banking.service;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.AccountRepository;
import com.nlogax.banking.web.dto.AccountDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService  {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public boolean exists (Long id) {
        return repository.existsById(id);
    }

    public boolean update (AccountDto accountDto) {
        Long id = Long.parseLong(accountDto.getId());
        Optional<Account> account = repository.findById(id);

        if (account.isPresent()) {
            account.get().setName(accountDto.getName());
            account.get().setCurrency(accountDto.getCurrency());
        }

        return account.isPresent();
    }

    // todo should be accessible only for admins
    public Account get (Long id) {
        Optional<Account> account = repository.findById(id);
        return account.orElse(null);
    }

    public Account save (AccountDto accountDto) {
        System.out.println("saving: ");
        System.out.println(accountDto.toString());
        if (accountDto.getId() != null) {
            // if passed accountDto has id, then it means that it's been passed from front, newly created
            // we return null if an account is already created: is this right?
            return null;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        Account account;
        account = new Account(user, accountDto.getName(), accountDto.getCurrency());
        account.setBalance(100);
        user.getAccounts().add(account);

        repository.save(account);
        return account;
    }
}
