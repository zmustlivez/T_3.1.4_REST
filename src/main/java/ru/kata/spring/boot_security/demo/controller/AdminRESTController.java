package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminRESTController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminRESTController(@Qualifier("userServiceImplRepo") UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/principal")
    public User getPrincipal() {
        return userService.loadUserByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @PostMapping("/save")
    @Transactional
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/find")
    public User searchUserByUsername(@RequestParam String username) {
        return userService.loadUserByUsername(username);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.loadUserByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
    }
}


