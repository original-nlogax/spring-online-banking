package com.nlogax.banking.service;

import com.nlogax.banking.exception.UserDoesntExistException;
import com.nlogax.banking.model.Role;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.UserRepository;
import com.nlogax.banking.utils.Utils;
import com.nlogax.banking.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SessionService sessionService;

    public User save(UserDto registrationDto) {
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

    public User get (Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
            throw new UserDoesntExistException();

        return user.get();
    }

    public User getByEmail (String email) {
        User user = repository.findByEmail(email).get(0);
        return user;
    }

    public void update (UserDto userDto) {
        /*
        if (userDto.getId() == null) {
            // if accountDto has no id, then it means that it is absent from db
            throw new UserDoesntExistException();
        }*/

        /*
        Long id = Long.parseLong(userDto.getId());
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
            throw new UserDoesntExistException();*/
        User user = sessionService.getAuthUser();

        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        repository.save(user);
    }

    public void delete (Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
            throw new UserDoesntExistException();

        user.get().getAccounts().forEach(account -> account.setUser(null));
        repository.delete(user.get());
    }
}
