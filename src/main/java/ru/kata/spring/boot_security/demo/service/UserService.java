package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Transactional
public interface UserService {
    void addUser(User user);

    User loadUserByUsername(String name);

    List<User> getUsers();

    void updateUser(User user);

    void deleteUser(String name);

}
