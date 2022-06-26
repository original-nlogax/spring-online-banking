package com.nlogax.banking.service;

import com.nlogax.banking.model.User;
import com.nlogax.banking.web.dto.UserRegistrationDto;

public interface UserService {

    User save(UserRegistrationDto registrationDto);
}
