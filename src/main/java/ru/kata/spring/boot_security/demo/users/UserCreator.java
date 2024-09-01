package ru.kata.spring.boot_security.demo.users;

import org.springframework.beans.factory.annotation.Qualifier;
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

    public UserCreator(RoleRepository roleRepository, @Qualifier("userServiceImplRepo")UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    private void postConstruct() {
/*        Optional<Role> existingRole = roleRepository.findByRolename(Roles.ADMIN.getDescription());

        Optional<Role> adminRole;
        if (existingRole.isEmpty()) {
            adminRole = Optional.of(roleRepository.save(new Role(Roles.ADMIN.getDescription())));
        } else {
            adminRole = existingRole;
        }*/

        Role role = roleRepository.findByRolename(Roles.ADMIN.getDescription()).orElse(null);
        User admin = new User(
                "admin@mail.ru",
                "admin123A",
                "Creator",
                role);
        if (!userService.getUsers().contains(admin)) {
            userService.addUser(admin);
        }
        role=roleRepository.findByRolename(Roles.USER.getDescription()).orElse(null);
        User user = new User(
                "user@mail.ru",
                "1234",
                "Creator",
                role);
        if (!userService.getUsers().contains(user)) {
            userService.addUser(user);
        }
//        userService.addUser(admin);
    }

}
