package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.entity.User;
import com.example.privacyprotection.mapper.UserMapper;
import com.example.privacyprotection.service.UserService;
import com.example.privacyprotection.utils.JWTUtils;
import com.example.privacyprotection.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private MD5 md5;

    @Value("${upload.avatarUrl}")
    private String avatarUrl;

    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = userMapper.checkUsername(username);
            if (user == null) {
                userMapper.register(username, md5.MD5Encode(password), "guest.png");
                map.put("code", 1);
                map.put("msg", "success");
            } else {
                map.put("code", 0);
                map.put("msg", "already exist");
            }
        } catch (Exception exception) {
            map.put("code", -2);
            map.put("msg", "error");
        }

        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.getUserByUsername(username);
        if (user != null) {
            if (md5.MD5Encode(password).equals(user.getPassword())) {
                String token = jwtUtils.getToken(user.getId(), user.getUsername());
                userMapper.login(user.getId(), token);
                map.put("code", 1);
                map.put("token", token);
            } else {
                map.put("code", 0);
                map.put("msg", "Error Password");
            }
        } else {
            map.put("code", -2);
            map.put("msg", "Error Username");
        }
        return map;
    }

    public Map<String, Object> logout(String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", jwtUtils.cleanToken(token));
        return map;
    }

    public Map<String, Object> getUserInfo(String token) {
        Map<String, Object> map = new HashMap<>();
        int id = jwtUtils.checkToken(token);
        User user = userMapper.selectById(id);
        if (user != null) {
            map.put("id", user.getId());
            map.put("name", user.getUsername());
            map.put("avatar", avatarUrl + user.getAvatar());
            map.put("code", 1);
            map.put("msg", "success");
        } else {
            map.put("code", -1);
            map.put("msg", "token error");
        }
        return map;
    }
}
