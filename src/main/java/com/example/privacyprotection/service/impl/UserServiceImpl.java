package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.mapper.UserMapper;
import com.example.privacyprotection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
}
