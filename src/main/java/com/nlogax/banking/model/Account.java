package com.nlogax.banking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nlogax.banking.enums.Currency;
import com.nlogax.banking.utils.CreditCardNumberGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    public static String BIN_NUMBER = "423";    // todo use variable from bank.properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float balance;
    private Currency currency;
    private String number;

    //@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  // todo why does EAGER crashes?
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Account(User user, String name, Currency currency) {
        this.user = user;
        this.name = name;
        this.currency = currency;
        number = new CreditCardNumberGenerator().generate(BIN_NUMBER, 16);
    }

    public Account(String name, Currency currency) {
        this(null, name, currency);
    }

    public void deposit (float amount) {
        this.balance += amount;
    }

    public void withdraw (float amount) {
        this.balance -= amount;
    }
}
