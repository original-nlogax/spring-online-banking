package com.nlogax.banking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nlogax.banking.enums.Currency;
import com.nlogax.banking.utils.CreditCardNumberGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Value("${bank.bin_number:123}")    // todo properties value
    public static String BIN_NUMBER;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  // todo
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private String name;
    private Currency currency;
    private BigDecimal balance;
    private String number;

    public Account(User user, String name, Currency currency) {
        this.user = user;
        this.name = name;
        this.currency = currency;
        number = new CreditCardNumberGenerator().generate(BIN_NUMBER, 16);
    }

    public Account(String name, Currency currency) {
        this(null, name, currency);
    }

    public void deposit (BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw (BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
