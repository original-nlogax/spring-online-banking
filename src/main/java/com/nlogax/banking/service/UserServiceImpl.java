package com.nlogax.banking.service;

import com.nlogax.banking.model.Role;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.UserRepository;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        // TODO bloat
        List<Role> roles;
        boolean isAdmin = registrationDto.getEmail().equals("admin");
        if (!isAdmin) roles = Collections.singletonList(new Role("ROLE_USER"));
        else roles = List.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER"));

        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                registrationDto.getPassword(), roles);

        System.out.println("Saving new user [" + registrationDto.getEmail() + "] (admin = " + isAdmin + ")");

        return userRepository.save(user);
    }

}
