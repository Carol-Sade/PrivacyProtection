package com.example.privacyprotection.controller;

import com.example.privacyprotection.service.UserService;
import com.example.privacyprotection.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

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

    @GetMapping("/resetUsername")
    public Map<String, Object> resetUsername(@RequestHeader String token,
                                             @RequestParam String newUsername) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = userService.resetUsername(userId, newUsername);
            if (code == 1) {
                map.put("code", 1);
                map.put("msg", "success");
            } else if (code == -2) {
                map.put("code", -2);
                map.put("msg", "username exist");
            } else {
                map.put("code", 0);
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/resetPassword")
    public Map<String, Object> resetPassword(@RequestHeader String token,
                                             @RequestParam String oldPassword,
                                             @RequestParam String newPassword) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = userService.resetPassword(userId, oldPassword, newPassword);
            if (code == 1) {
                jwtUtils.cleanToken(token);
                map.put("code", 1);
                map.put("msg", "success");
            } else if (code == -2) {
                map.put("code", -2);
                map.put("msg", "wrong password");
            } else {
                map.put("code", 0);
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/resetAvatar")
    public Map<String, Object> resetAvatar(@RequestHeader String token,
                                           @RequestParam MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = userService.resetAvatar(userId, file);
            if (code == 1) {
                map.put("code", 1);
                map.put("msg", "success");
            } else {
                map.put("code", 0);
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
