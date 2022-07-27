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

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (numberFrom != null ? !numberFrom.equals(that.numberFrom) : that.numberFrom != null) return false;
        if (numberTo != null ? !numberTo.equals(that.numberTo) : that.numberTo != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return currency == that.currency;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (numberFrom != null ? numberFrom.hashCode() : 0);
        result = 31 * result + (numberTo != null ? numberTo.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }*/
}
