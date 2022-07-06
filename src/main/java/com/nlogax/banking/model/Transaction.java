package com.nlogax.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numberFrom;
    private String numberTo;
    private float amount;

    public Transaction(String numberFrom, String numberTo, float amount) {
        this.numberFrom = numberFrom;
        this.numberTo = numberTo;
        this.amount = amount;
    }
}
