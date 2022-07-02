package com.nlogax.banking.controller;

import com.nlogax.banking.service.UserService;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/new")
    public ResponseEntity<Void> register (RedirectAttributes ra,
                                          @ModelAttribute("userData") UserRegistrationDto data) {  // todo validation
        ra.addFlashAttribute("registered", "true");
        service.save(data);
        

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
