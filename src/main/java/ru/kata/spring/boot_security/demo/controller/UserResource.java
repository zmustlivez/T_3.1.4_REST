/*
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Optional;

@RequestMapping("/api/v1/user")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public User findByUsername(@PathVariable String username) {
        if (username != null && !username.isEmpty()) {
            User user;
            if ((user = userService.loadUserByUsername(username)) != null) {
                return user;
            }
        } else throw new NullPointerException("User not found");
    }
}*/
