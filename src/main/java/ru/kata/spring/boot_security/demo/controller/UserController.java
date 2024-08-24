package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(@Qualifier("userServiceImplRepo") UserService userService) {
        this.userService = userService;
    }

    @GetMapping//    @PreAuthorize("hasRole('USER')")
    public String getUserPage(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "user-page";
    }
}
