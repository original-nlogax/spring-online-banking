package com.nlogax.banking.controller;

import com.nlogax.banking.service.UserService;
import com.nlogax.banking.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "true");
        }
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginPage (Model model,
                                 @RequestParam(value = "error", required = false) String error,
                                 @RequestParam(value = "registered", required = false) String registered,
                                 @RequestParam(value = "logout", required = false) String logout) {
        model.addAttribute("error", error);
        model.addAttribute("registered", registered);
        model.addAttribute("logout", logout);
        return "login";
    }

    @RequestMapping("/registration")
    public String showRegistrationPage (Model model,
                                        @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("error", error);
        return "registration";
    }

    @PostMapping("/user/new")
    public String register (RedirectAttributes redirectAttributes, @ModelAttribute("userData") UserRegistrationDto data) {  // todo validation
        redirectAttributes.addAttribute("registration", "true");
        userService.save(data);
        return "redirect:/login";
    }
}
