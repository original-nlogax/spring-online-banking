package com.nlogax.banking.controller.api;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.service.AccountService;
import com.nlogax.banking.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService service;

    /*
    @HeadMapping(path="/{id}")
    public ResponseEntity<Void> exists (@PathVariable Long id) {
        return service.exists(id) ? ok().build() : notFound().build();
    }*/

    //todo @SecurityRequirement(name = "role?")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> get (@PathVariable Long id) {
        Account account = service.get(id);
        return ok(account);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update (@PathVariable Long id, @ModelAttribute("accountDto") AccountDto data) {
        service.update(data);
        return ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> add (@ModelAttribute("accountDto") AccountDto data) {
        service.save(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    //    on every:
    //@ResponseStatus(NO_CONTENT)
    //@SecurityRequirement(name = "token")
    //@Operation(summary = "Delete user")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        service.delete(id);
        return ok().build();
    }

}
