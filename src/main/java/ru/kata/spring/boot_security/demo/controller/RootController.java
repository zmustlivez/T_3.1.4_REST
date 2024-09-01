package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String redirectToLogin() {
        return "redirect:/login";
    }

}
