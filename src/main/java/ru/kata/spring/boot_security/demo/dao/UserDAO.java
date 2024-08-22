package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {

    boolean addUser(User user);

    User loadUserByUsername(String name);

    List<User> getUsers();

    void updateUser(User user);

    void deleteUser(User user);
}
