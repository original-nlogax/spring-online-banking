package com.nlogax.banking.controller;

import com.nlogax.banking.service.AccountService;
import com.nlogax.banking.web.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@RestController
@RequestMapping("/accounts")
public class AccountController extends WebMvcConfigurerAdapter {

    @Autowired
    AccountService service;

    @PostMapping("/edit")
    public ResponseEntity<Void> editAccount (@ModelAttribute("accountDto") AccountDto data) {  // todo validation
        service.save(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
