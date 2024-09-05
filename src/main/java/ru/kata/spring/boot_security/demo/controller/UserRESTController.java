package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserRESTController {
    private final UserService userService;

    public UserRESTController(@Qualifier("userServiceImplRepo") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/principal")
    public User getPrincipal() {
        return userService.loadUserByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }
}
