package com.nlogax.banking.enums;

import java.math.BigDecimal;

public enum Currency {
    USD(0),
    EUR(1),
    CNY(2),
    AUD(3),
    RUB(4);

    private final int id;

    Currency(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public static BigDecimal Convert (Currency from, Currency to, BigDecimal amount) {
        BigDecimal converted = BigDecimal.valueOf(0.0);

        return converted;
    }
}