package com.nlogax.banking.controller.api;

import com.nlogax.banking.enums.Currency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @GetMapping()
    public ResponseEntity<Currency[]> get () {
        return ok(Currency.values());
    }
}
