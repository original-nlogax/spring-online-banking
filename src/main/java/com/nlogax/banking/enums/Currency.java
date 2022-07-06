package com.nlogax.banking.enums;

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
}