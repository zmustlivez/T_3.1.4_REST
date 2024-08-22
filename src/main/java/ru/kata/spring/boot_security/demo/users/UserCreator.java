package ru.kata.spring.boot_security.demo.users;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class UserCreator {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private Optional<Role> adminRole;

    public UserCreator(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    private void postConstruct() {
        Optional<Role> existingRole = roleRepository.findByRolename(Roles.ADMIN.getDescription());

        if (existingRole.isEmpty()) {
            adminRole = Optional.of(roleRepository.save(new Role(Roles.ADMIN.getDescription())));
        } else {
            adminRole = existingRole;
        }

        Role role = adminRole.orElse(null);
        User admin = new User(
                "admin",
                "admin123A",
                "Creator",
                role);
        if (!userService.getUsers().contains(admin)) {
            userService.addUser(admin);
        }
    }

}
