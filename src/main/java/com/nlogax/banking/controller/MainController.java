package com.nlogax.banking.controller;

import com.nlogax.banking.model.User;
import com.nlogax.banking.service.UserService;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController extends WebMvcConfigurerAdapter {

    @Autowired
    UserService userService;


    @RequestMapping("/adm")
    public String showAdminPanel () {
        return "adm";
    }


    @RequestMapping("/")
    public String showMainPage (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) model.addAttribute("admin", "true");
            User user = (User) auth.getPrincipal();

            //model.addAttribute("user", user); // fixme crash
            System.out.println(user.getAccounts());

            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("password", user.getPassword());
            model.addAttribute("phoneNumber", user.getPhoneNumber());
            model.addAttribute("accounts", user.getAccounts());
        }

        return "index";
    }

    /*
    @RequestMapping(value = {"/logout"})
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login?logout=true";
    }*/

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

    @PostMapping("/user/new")
    public String register (RedirectAttributes ra,
                            @ModelAttribute("userData") UserRegistrationDto data) {  // todo validation
        ra.addFlashAttribute("registered", "true");
        userService.save(data);
        return "redirect:/login";
    }
}
