package ru.kata.spring.boot_security.demo.dao;

import java.util.List;

public interface UserDAO<User> {

    boolean addUser(User user);

    User loadUserByUsername(String name);

    List<User> getUsers();

    void updateUser(User user);

    void deleteUser(User user);
}
