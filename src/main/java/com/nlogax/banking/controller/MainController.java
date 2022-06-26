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
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "true");
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
