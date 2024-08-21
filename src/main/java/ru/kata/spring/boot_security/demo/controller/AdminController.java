package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String showAddUserForm(@ModelAttribute("user") User user) {
        return "save-user-form";
    }


    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        boolean userAdded = userService.addUser(user);
        if (userAdded) {
            return "redirect:/admin";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User with username '" + user.getUsername() + "' already exists.");
            return "redirect:/admin/add";
        }
    }

    @GetMapping("/find")
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

    @GetMapping("/user-not-found")
    public String showUserNotFound() {
        return "user-not-found";
    }

    @GetMapping("/getInfo")
    public String getUserByName(@RequestParam("name") String name, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(name));
        return "user-page";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("usersList", userService.getUsers());
        if (model.containsAttribute("searchedUser")) {
            model.addAttribute("showSearchResult", true);
        }

        return "admin";
    }

    @PostMapping("/update")
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

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("name") String name) {
        userService.deleteUser(name);
        return "redirect:/admin";
    }

}
