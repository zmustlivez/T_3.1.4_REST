package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);

    User loadUserByUsername(String name);

    List<User> getUsers();

    void updateUser(User user);

    void deleteUser(String name);

}
