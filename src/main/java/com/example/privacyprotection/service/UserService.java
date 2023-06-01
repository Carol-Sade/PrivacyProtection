package com.example.privacyprotection.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {

    Map<String, Object> register(String username, String password);

    Map<String, Object> login(String username, String password);

    Map<String, Object> logout(String token);

    Map<String, Object> getUserInfo(String token);

    Integer resetUsername(Integer userId, String newUsername);

    Integer resetPassword(Integer userId, String oldPassword, String newPassword);

    Integer resetAvatar(Integer userId, MultipartFile file);

}
