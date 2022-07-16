package com.nlogax.banking.controller.api;

import com.nlogax.banking.dto.UserDto;
import com.nlogax.banking.model.User;
import com.nlogax.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService service;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> get (@PathVariable Long id) {
        User user = service.get(id);
        return ok(user);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/")
    public ResponseEntity<List<User>> getAll () {
        List<User> users = service.getAll();
        return ok(users);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        service.delete(id);
        return ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update (@PathVariable Long id, UserDto data) {
        System.out.println(id);
        User user = service.get(id);
        service.update(user, data);
        return ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> register (@ModelAttribute("userRegistrationDto") UserDto data) {  // todo validation
        service.save(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
