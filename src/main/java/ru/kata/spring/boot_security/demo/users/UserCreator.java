package ru.kata.spring.boot_security.demo.users;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class UserCreator {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private UserService userService;

    public UserCreator(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    private void postConstruct() {
        User admin = new User(
                "admin",
                "user123A",
                "Mike",
                roleRepository.save(new Role(Roles.ADMIN.getDescription())));
        userService.addUser(admin);

        User user = new User(
                "user",
                "user123U",
                "Ivan",
                roleRepository.save(new Role(Roles.USER.getDescription())));
        userService.addUser(user);
    }

}
