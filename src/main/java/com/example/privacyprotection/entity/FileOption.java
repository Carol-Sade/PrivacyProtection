package com.example.privacyprotection.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class FileOption {
    @Id
    private Integer id;
    private Integer optionUserId;
    private Integer fileId;
    private String optionName;
    private Timestamp createTime;
}
