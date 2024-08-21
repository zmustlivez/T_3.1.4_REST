package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {


    boolean addUser(User user);

    User loadUserByUsername(String name);

    List<User> getUsers();

    void updateUser(User user);

    void deleteUser(User user);
}
