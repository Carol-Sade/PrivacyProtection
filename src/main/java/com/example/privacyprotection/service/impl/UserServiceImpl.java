package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.entity.User;
import com.example.privacyprotection.mapper.UserMapper;
import com.example.privacyprotection.service.UserService;
import com.example.privacyprotection.utils.JWTUtils;
import com.example.privacyprotection.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private MD5 md5;

    @Value("${upload.avatarLocation}")
    private String avatarLocation;

    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (userMapper.checkUsername(username) == null) {
            userMapper.register(username, md5.MD5Encode(password), avatarLocation + "guest.png");
            map.put("code", 1);
        } else {
            map.put("code", -1);
        }
        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.getUserByUsername(username);
        if (user != null) {
            if (md5.MD5Encode(password).equals(user.getPassword())) {
                String token = jwtUtils.getToken(username, password);
                userMapper.updateToken(user.getId(), token);
                map.put("code", 1);
                map.put("token", token);
            } else {
                map.put("code", 0);
                map.put("msg", "Error Password");
            }
        } else {
            map.put("code", -1);
            map.put("msg", "Error Username");
        }
        return map;
    }
}
