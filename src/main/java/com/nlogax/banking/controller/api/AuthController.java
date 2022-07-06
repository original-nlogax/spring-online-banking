package com.nlogax.banking.controller.api;

import com.nlogax.banking.model.User;
import com.nlogax.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService service;

    @GetMapping(path = "/user") /*, produces = MediaType.APPLICATION_JSON_VALUE*/
    public ResponseEntity<User> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Object principal = auth.getPrincipal();

        if (principal instanceof User)
            return ok((User) principal);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}