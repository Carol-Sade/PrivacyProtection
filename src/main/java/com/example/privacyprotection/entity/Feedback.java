package com.example.privacyprotection.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Feedback {
    @Id
    private Integer id;
    private String content;
    private Integer userId;
    private Timestamp createTime;
}
