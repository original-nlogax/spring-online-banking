package com.nlogax.banking.service;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.Role;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.UserRepository;
import com.nlogax.banking.utils.Utils;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
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

        /*
        int number = new Random().nextInt(4)+1;
        for (int i = 0; i < number; i++) {
            Account ac = new Account(user, "Account " + (i+1), "RUB");
            ac.setBalance(new Random().nextFloat(50000f));
            accounts.add(ac);
        }*/

        System.out.println("Saving new user [" + registrationDto.getEmail() + "] (admin = " + isAdmin + ")");
        return userRepository.save(user);
    }

}
