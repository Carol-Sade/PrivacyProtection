package com.example.privacyprotection.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class FileComment {
    @Id
    private Integer id;
    private Integer userId;
    private Integer fileId;
    private String content;
    private Timestamp createTime;
}
