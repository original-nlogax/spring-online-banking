package com.nlogax.banking.service;

import com.nlogax.banking.exception.UserUnauthenticatedException;
import com.nlogax.banking.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    // todo maybe make static? and remove injections
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            throw new UserUnauthenticatedException();

        Object principal = auth.getPrincipal();

        if (principal instanceof User)
            return (User) principal;
        else
            throw new IllegalArgumentException();
    }
}
