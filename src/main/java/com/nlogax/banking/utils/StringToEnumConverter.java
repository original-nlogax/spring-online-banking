package com.nlogax.banking.utils;

import com.nlogax.banking.enums.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// converting currency string name from passed requests to Currency enum
@Component
public class StringToEnumConverter implements Converter<String, Currency> {
    @Override
    public Currency convert(String source) {
        return Currency.valueOf(source.toUpperCase());
    }
}