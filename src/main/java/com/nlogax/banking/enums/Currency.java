package com.nlogax.banking.enums;

public enum Currency {
    UAH(0),
    RUB(1),
    USD(2),
    EUR(3);

    private final int id;

    Currency(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}