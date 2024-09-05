package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exceptions.UserExistException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImplRepo implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImplRepo(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User addUser(User user) {
        List<Role> rolesList = roleRepository.findAll();
        Set<Role> userRoles = new HashSet<>(); // Используем Set, чтобы избежать дубликатов

        for (Role role : user.getRoles()) {
            // Проверяем, существует ли роль в списке rolesList
            Role existingRole = rolesList.stream()
                    .filter(r -> r.getRolename().equals(role.getRolename()))
                    .findFirst()
                    .orElse(null);

            if (existingRole == null) {
                // Если роли нет в списке, сохраняем новую роль
                existingRole = roleRepository.save(role);
            }

            // Добавляем роль к набору ролей пользователя
            userRoles.add(existingRole);
        }

        user.setRoles(new ArrayList<>(userRoles));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserExistException("User with " + user.getUsername() + " is exist");
        }
        return userRepository.save(user);
    }


    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String name) {
        return userRepository.findByUsername(name);

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();

    }

    @Override
    @Transactional
    public User updateUser(String username, User user) {
        User existingUser= userRepository.findByUsername(username);
        System.out.println(user);
        if (user != null) {
//            existingUser = userRepository.findByUsername(username);
            if (!user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (!user.getNameowner().isEmpty()) {
                existingUser.setNameowner(user.getNameowner());
            }
            if (!user.getRoles().isEmpty() & !user.getRoles().equals(existingUser.getRoles())) {
                existingUser.setRoles(user.getRoles());
            }
        }
        return userRepository.save(existingUser);

    }

    @Override
    @Transactional
    public void deleteUser(String name) {
        userRepository.delete(loadUserByUsername(name));

    }
}
