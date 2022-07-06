package com.nlogax.banking.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/registration")
    public String registration() {
        return "registration";
    }

    @RequestMapping("/payments")
    public String payments() {
        return "payments";
    }

}
