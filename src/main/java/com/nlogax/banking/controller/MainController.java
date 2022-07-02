package com.nlogax.banking.controller;

import com.nlogax.banking.model.User;
import com.nlogax.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController extends WebMvcConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @RequestMapping("/adm")
    public String showAdminPanel () {
        return "adm";
    }

    @RequestMapping("/")
    public String showMainPage (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            User user = (User) auth.getPrincipal();
            model.addAttribute("user", user);

            if (user.getRoles().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN")))
                model.addAttribute("isAdmin", "true");
        }

        return "index";
    }

    @RequestMapping("/payments")
    public String showPaymentsPage (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            User user = (User) auth.getPrincipal();
            model.addAttribute("user", user);

            if (user.getRoles().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN")))
                model.addAttribute("isAdmin", "true");
        }

        return "payments";
    }

    // todo attribute vs parameter
    @RequestMapping("/login")
    public String showLoginPage (Model model, HttpServletRequest request,
                                 @RequestParam(value = "error", required = false) String error,
                                 @ModelAttribute(value = "registered") String registered,
                                 @RequestParam(value = "logout", required = false) String logout) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        //model.addAttribute("registered", registered);
        // greyed out because ModelAttribute docs:
        // the argument’s fields should be populated (automatically)
        // from all request parameters that have matching names."
        return "login";
    }

    @RequestMapping("/registration")
    public String showRegistrationPage (Model model,
                                        @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("error", error);
        return "registration";
    }
}
