package com.nlogax.banking.security;

import com.nlogax.banking.model.User;
import com.nlogax.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //PasswordEncoder encoder =
        //        PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("Trying to log in with name=["+name+"], pass=["+password+"]...");

        // if we didn't write custom auth provider then we'd write complex
        // sql queries which isn't probably good by hibernate standard

        User user = userService.findByEmail(name);

        //if (encoder.matches(password, user.get(0).getPassword())) {
        if (password.equals(user.getPassword())) {

            List<SimpleGrantedAuthority> authorities =
                    user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();

            System.out.println("User [" + user.getEmail() + "] has logged in with roles: [" +
                    authorities.stream().map(Object::toString).reduce("", String::concat) + "]");

            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}