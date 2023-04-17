package com.example.privacyprotection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privacyprotection.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getUsers();

    User checkUsername(String username);

    User getUserByUsername(String username);

    User getUserByToken(String token);

    boolean updateToken(Integer id, String token);

    boolean login(Integer id, String token);

    boolean cleanToken(String token, String reset);

    boolean register(String username, String password, String avatar);
}
