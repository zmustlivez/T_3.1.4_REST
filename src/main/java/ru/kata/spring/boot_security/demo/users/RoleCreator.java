package ru.kata.spring.boot_security.demo.users;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class RoleCreator {
    private final RoleRepository roleRepository;

    public RoleCreator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void createRole() {
        Arrays.stream(Roles.values()).forEach(e -> {
            Role role = new Role();
            role.setRolename(e.getDescription());
            roleRepository.save(role);
        });
    }
}
