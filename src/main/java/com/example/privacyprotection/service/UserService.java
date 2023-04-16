package com.example.privacyprotection.service;

import com.example.privacyprotection.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface UserService {
    List<User> getUsers();

    Map<String, Object> register(String username, String password);

    Map<String, Object> login(String username, String password);

    Map<String, Object> logout(String token);

    Map<String, Object> getUserInfo(String token);
}
