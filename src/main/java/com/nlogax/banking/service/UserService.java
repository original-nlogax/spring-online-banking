package com.nlogax.banking.service;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.Role;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.UserRepository;
import com.nlogax.banking.utils.Utils;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(UserRegistrationDto registrationDto) {
        boolean isAdmin = registrationDto.getEmail().equals("123");

        List<Role> roles = new ArrayList<>();
        if (isAdmin) roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));

        List<Account> accounts = new ArrayList<>();

        User user = new User(
                Utils.capitalizeFirstLetter(registrationDto.getFirstName()),
                Utils.capitalizeFirstLetter(registrationDto.getLastName()), registrationDto.getEmail(), registrationDto.getPhoneNumber(),
                registrationDto.getPassword(), roles, accounts);

        System.out.println("Saving new user [" + registrationDto.getEmail() + "] (admin = " + isAdmin + ")");
        return repository.save(user);
    }

}
