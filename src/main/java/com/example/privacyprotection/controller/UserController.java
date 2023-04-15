package com.example.privacyprotection.controller;

import com.example.privacyprotection.entity.User;
import com.example.privacyprotection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("register")
    public Map<String, Object> register(@RequestParam String username,
                                         @RequestParam String password) {
        return userService.register(username, password);
    }

    @GetMapping("login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {
        return userService.login(username, password);
    }
}
