package com.nlogax.banking.controller.api;

import com.nlogax.banking.model.User;
import com.nlogax.banking.service.UserService;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService service;

    /*
    @GetMapping
    public List<User> getAll () {
        return service.getAll();
    }*/

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> get (@PathVariable Long id) {
        User user = service.get(id);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // or notFound().build() ?
        else return ok(user);
    }

    @DeleteMapping(value = "/{id}")
    //    on every:
    //@ResponseStatus(NO_CONTENT)
    //@SecurityRequirement(name = "token")
    //@Operation(summary = "Delete user")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        boolean valid = true;   //todo
        if (!valid) return badRequest().build();

        boolean success = service.delete(id);

        if (success) return ok().build();
        else return notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> register (@ModelAttribute("userRegistrationDto") UserRegistrationDto data) {  // todo validation
        System.out.println(data);
        service.save(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
