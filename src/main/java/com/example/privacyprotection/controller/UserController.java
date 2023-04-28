package com.example.privacyprotection.controller;

import com.example.privacyprotection.entity.User;
import com.example.privacyprotection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/user")
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

    @PostMapping("login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {
        return userService.login(username, password);
    }

    @GetMapping("getUserInfo")
    public Map<String, Object> getUserInfo(@RequestHeader String token) {
        return userService.getUserInfo(token);
    }

    @PostMapping("logout")
    public Map<String, Object> logout(@RequestHeader String token) {
        return userService.logout(token);
    }
}
