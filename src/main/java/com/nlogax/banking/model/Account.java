package com.nlogax.banking.model;

import com.nlogax.banking.utils.CreditCardNumberGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.nlogax.banking.model.User;
import org.hibernate.type.CurrencyType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float balance;
    private String currency;
    private String number;

    //@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // todo why does EAGER crashes?
    @ManyToOne
    private User user;

    public Account(User user, String name, String currency) {
        super();   //fixme
        this.user = user;
        this.name = name;
        this.currency = currency;
        number = new CreditCardNumberGenerator().generate("423", 16);
        System.out.println("number = " + number);
    }

    public Account(String name, String currency) {
        this(null, name, currency);
    }

    public void deposit (float amount) {

    }

    public void withdraw (float amount) {

    }

    public String getFormattedNumber () {
        return "**** " + number.substring(number.length()-4);
    }

    // todo use?
    public String getHTML () {
        return getFormattedNumber() + ' ' + name + " <span class='test-success'>(" + balance + " " + currency + " </span>";
    }
}
