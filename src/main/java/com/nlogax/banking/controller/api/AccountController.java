package com.nlogax.banking.controller.api;

import com.nlogax.banking.dto.AccountDto;
import com.nlogax.banking.model.Account;
import com.nlogax.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService service;


    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> get (@PathVariable Long id) {
        Account account = service.get(id);
        return ok(account);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update (@PathVariable Long id, @ModelAttribute("accountDto") AccountDto data) {
        Account account = service.get(id);
        service.update(account, data);
        return ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> create (@ModelAttribute("accountDto") AccountDto data) {
        service.save(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        service.delete(id);
        return ok().build();
    }

}
