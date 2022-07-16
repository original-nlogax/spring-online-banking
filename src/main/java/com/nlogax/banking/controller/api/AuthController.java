package com.nlogax.banking.controller.api;

import com.nlogax.banking.model.User;
import com.nlogax.banking.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    SessionService sessionService;

    @GetMapping(path = "/user") /*, produces = MediaType.APPLICATION_JSON_VALUE*/
    public ResponseEntity<User> getAuthUser() {
        User user = sessionService.getAuthUser();
        return ok(user);
    }



    /*
    public static boolean can (boolean ) {

    }*/
}