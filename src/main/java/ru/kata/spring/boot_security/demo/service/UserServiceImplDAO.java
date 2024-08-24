package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImplDAO implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDAO<User> userDAO;

public UserServiceImplDAO(PasswordEncoder passwordEncoder, UserDAO<User> userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.addUser(user);
    }


    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String name) {
        return userDAO.loadUserByUsername(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDAO.getUsers();

    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);

    }

    @Override
    @Transactional
    public void deleteUser(String name) {
        userDAO.deleteUser(loadUserByUsername(name));

    }
}
