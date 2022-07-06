package com.nlogax.banking.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// numberFrom and numberTo -- from and to db
public class TransactionDto {
    private String id;
    private String numberFrom;
    private String numberTo;
    private float amount;
}
