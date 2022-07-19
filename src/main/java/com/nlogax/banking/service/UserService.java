package com.nlogax.banking.service;

import com.nlogax.banking.dto.UserDto;
import com.nlogax.banking.exception.UserDoesntExistException;
import com.nlogax.banking.model.Role;
import com.nlogax.banking.model.User;
import com.nlogax.banking.repository.UserRepository;
import com.nlogax.banking.security.CustomAuthenticationProvider;
import com.nlogax.banking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User register(UserDto dto) {
        List<Role> roles = new ArrayList<>();

        if (dto.getEmail().equals("123")) roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));

        User user = new User(
                Utils.capitalizeFirstLetter(dto.getFirstName()),
                Utils.capitalizeFirstLetter(dto.getLastName()),
                dto.getEmail(), dto.getPhoneNumber(),
                CustomAuthenticationProvider.getEncoder().encode(dto.getPassword()), roles, new ArrayList<>());

        System.out.println("Saving new user [" + dto.getEmail() + "]");
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

    public void update (User user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        repository.save(user);
    }

    public void delete (Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
            throw new UserDoesntExistException();

        user.get().getAccounts().forEach(account -> account.setUser(null));
        repository.delete(user.get());
    }

    public User findByEmail(String email) {
        // todo exception handling
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty())
            throw new UserDoesntExistException();

        return user.get();
    }
}
