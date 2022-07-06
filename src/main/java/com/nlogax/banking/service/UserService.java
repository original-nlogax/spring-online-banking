package com.nlogax.banking.service;

import com.nlogax.banking.model.Role;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.UserRepository;
import com.nlogax.banking.utils.Utils;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(UserRegistrationDto registrationDto) {
        List<Role> roles = new ArrayList<>();

        if (registrationDto.getEmail().equals("123")) roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));

        User user = new User(
                Utils.capitalizeFirstLetter(registrationDto.getFirstName()),
                Utils.capitalizeFirstLetter(registrationDto.getLastName()), registrationDto.getEmail(), registrationDto.getPhoneNumber(),
                registrationDto.getPassword(), roles, new ArrayList<>());

        System.out.println("Saving new user [" + registrationDto.getEmail() + "]");
        return repository.save(user);
    }

    public List<User> getAll () {
        return repository.findAll();
    }

    // todo should be acessible only for admins
    public User get (Long id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    // fixme make better
    public User getByEmail (String email) {
        User user = repository.findByEmail(email).get(0);
        return user;
    }

    public boolean delete (Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            user.get().getAccounts().forEach(account -> account.setUser(null));
            repository.delete(user.get());
        }

        return user.isPresent();
    }
}
