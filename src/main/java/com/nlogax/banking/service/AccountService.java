package com.nlogax.banking.service;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.User;
import com.nlogax.banking.web.dto.AccountDto;
import com.nlogax.banking.web.dto.UserRegistrationDto;

public interface AccountService {

    Account save(AccountDto accountDto);
}
