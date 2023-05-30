package com.example.privacyprotection.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class User {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private String token;
    private Integer role;

    private Timestamp create_time;
    private Timestamp update_time;
}
