package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.users.Roles;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    List<Role> thisUserRoles;

    public AdminController(@Qualifier("userServiceImplRepo") UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping
    public String getUsers(Model model, Principal principal) {
/*        if (model.containsAttribute("searchedUser")) {
            model.addAttribute("showSearchResult", true);
        }*/
        thisUserRoles = userService.loadUserByUsername(principal.getName()).getRoles();
        model.addAttribute("usersList", userService.getUsers());
        model.addAttribute("loginRoles", thisUserRoles);
        model.addAttribute("rolesList", roleRepository.findAll());
        model.addAttribute("userForm", new User());
        model.addAttribute("activeTab", "users");
        boolean highLev = thisUserRoles.stream()
                .anyMatch(role -> role.getRolename().equals(Roles.ADMIN.getDescription()));
        model.addAttribute("highLev", highLev);
        model.addAttribute("thisUser", userService.loadUserByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping("/add")
    public String showAddUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("rolesList", roleRepository.findAll());
        return "save-user-form";
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user, Model model, Principal principal, @RequestParam(name = "activeTab", required = false) String activeTab) {
        userService.addUser(user);
        return "redirect:/admin";//TODO поправить сервис и repo (убрать возврат boolean)
    }

    @GetMapping("/find")
    public String searchUserByUsername(@RequestParam("username") String name, Model model) {
        if (name != null && !name.isEmpty()) {
            User user;
            if ((user = userService.loadUserByUsername(name)) != null) {
                model.addAttribute("user", user);
                return "find-result";
            }
        }
        return "user-not-found";
    }

    @GetMapping("/user-not-found")
    public String showUserNotFound() {
        return "user-not-found";
    }

    @GetMapping("/getInfo")
    public String getUserByName(@RequestParam("name") String name, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(name));
//        model.addAttribute("rolesList", roleRepository.findAll());
//        model.addAttribute("highLev", true);
        return "user-page";
    }

    @PostMapping("/update")
    public String updateUser(//@RequestParam("nameOfOwner") String nameOfOwner,//@RequestParam("password") String password,
                             //@ModelAttribute("user") List<Role> roles,
                             @ModelAttribute("userForm") User user) {
        if (user != null) {
            User existingUser = userService.loadUserByUsername(user.getUsername());
            System.out.println(existingUser);
            if (!user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (!user.getNameowner().isEmpty()) {
                existingUser.setNameowner(user.getNameowner());
            }
            if (!user.getRoles().isEmpty() & !user.getRoles().equals(existingUser.getRoles())) {
                existingUser.setRoles(user.getRoles());
            }

            userService.updateUser(existingUser);
            return "redirect:/admin";
//                    :/admin/getInfo?name=" + existingUser.getUsername();
        } else {
            return "user-not-found";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("name") String name) {
        userService.deleteUser(name);
        return "redirect:/admin";
    }
}
