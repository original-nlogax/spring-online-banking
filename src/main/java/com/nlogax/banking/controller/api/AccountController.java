package com.nlogax.banking.controller.api;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.service.AccountService;
import com.nlogax.banking.web.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> get (@PathVariable Long id) {
        Account account = service.get(id);
        if (account == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // or notFound().build() ?
        else return ok(account);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update (@PathVariable Long id, @ModelAttribute("accountDto") AccountDto data) {
        boolean valid = true;   //todo
        if (!valid) return badRequest().build();

        boolean success = service.update(data);

        if (success) return ok().build();
        else return notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> add (@ModelAttribute("accountDto") AccountDto data) {
        boolean valid = true;   //todo
        if (!valid) return badRequest().build();

        boolean success = service.save(data) != null;

        if (success) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);  // already exists
    }

    @DeleteMapping(value = "/{id}")
    //    on every:
    //@ResponseStatus(NO_CONTENT)
    //@SecurityRequirement(name = "token")
    //@Operation(summary = "Delete user")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        boolean valid = true;   //todo
        if (!valid) return badRequest().build();

        boolean success = service.delete(id);

        if (success) return ok().build();
        else return notFound().build();
    }

}
