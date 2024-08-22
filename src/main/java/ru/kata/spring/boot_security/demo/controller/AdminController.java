package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userPage(Model model, Principal principal) {
        // Логика для пользователей

        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "user-page";
    }

    @GetMapping("/admin")
    public String getUsers(Model model) {
        model.addAttribute("usersList", userService.getUsers());
        if (model.containsAttribute("searchedUser")) {
            model.addAttribute("showSearchResult", true);
        }

        return "admin";
    }

    @GetMapping("/admin/add")
    public String showAddUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("rolesList", roleRepository.findAll());
        return "save-user-form";
    }

    @PostMapping("/admin/save")
    public String addUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        boolean userAdded = userService.addUser(user);
        if (userAdded) {
            return "redirect:/admin";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User with username '" + user.getUsername() + "' already exists.");
            return "redirect:/admin/add";
        }
    }

    @GetMapping("/admin/find")
    public String searchUser(@RequestParam("username") String name, Model model) {
        if (name != null && !name.isEmpty()) {
            User user;
            if ((user = userService.loadUserByUsername(name)) != null) {
                model.addAttribute("user", user);
                return "find-result";
            }
        }
        return "user-not-found";
    }

    @GetMapping("/admin/user-not-found")
    public String showUserNotFound() {
        return "user-not-found";
    }

    @GetMapping("/admin/getInfo")
    public String getUserByName(@RequestParam("name") String name, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(name));
        model.addAttribute("highLev", true);
        return "user-page";
    }

    @PostMapping("/admin/update")
    public String updateUser(@RequestParam("password") String password, @ModelAttribute("user") User user) {
        if (user != null) {
            User existingUser = userService.loadUserByUsername(user.getUsername());
            existingUser.setPassword(password);
            userService.updateUser(existingUser);
            return "redirect:/admin/getInfo?name=" + existingUser.getUsername();
        } else {
            return "user-not-found";
        }
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam("name") String name) {
        userService.deleteUser(name);
        return "redirect:/admin";
    }
}
