package com.nlogax.banking.model;

import com.nlogax.banking.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime date;
    private String numberFrom;
    private String numberTo;
    private BigDecimal amount;
    private Currency currency;

    public Transaction(ZonedDateTime date, String numberFrom, String numberTo, BigDecimal amount, Currency currency) {
        this.date = date;
        this.numberFrom = numberFrom;
        this.numberTo = numberTo;
        this.amount = amount;
        this.currency = currency;
    }
}
