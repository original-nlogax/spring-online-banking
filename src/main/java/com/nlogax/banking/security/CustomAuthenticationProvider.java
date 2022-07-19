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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    /*
    @Autowired
    PasswordEncoder encoder;
    @PostConstruct
    private void init () {
        encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder () {
        return encoder;
    }*/

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static PasswordEncoder getEncoder() {
        return encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("Trying to log in with name=["+name+"], pass=["+password+"]...");

        User user = userService.findByEmail(name);

        System.out.println(encoder);
        if (encoder.matches(password, user.getPassword())) {
        //if (password.equals(user.getPassword())) {

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