package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")

    public String greeting(@AuthenticationPrincipal String user, Model model){
        return "greeting";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/users")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/user-create")
    public String createUserForm(User user) {
        return "user-create";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/user-create")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user-info")
    public String infoUserForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "/user-info";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/user-update")
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
}
