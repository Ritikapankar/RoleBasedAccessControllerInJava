package com.rbac.rbacAuth.controller;

import com.rbac.rbacAuth.entity.User;
import com.rbac.rbacAuth.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        return userService.register(user);
    }

    // Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        return userService.login(user);
    }
}