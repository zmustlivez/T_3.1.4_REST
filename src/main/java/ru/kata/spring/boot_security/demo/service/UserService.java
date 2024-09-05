package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Transactional
public interface UserService {
    User addUser(User user);

    User loadUserByUsername(String name);

    List<User> getUsers();

    User updateUser(String username, User user);

    void deleteUser(String name);

}
