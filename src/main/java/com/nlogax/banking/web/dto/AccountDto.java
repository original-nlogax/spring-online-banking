package com.nlogax.banking.web.dto;

import com.nlogax.banking.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String id;
    private String name;
    private Currency currency;
}
