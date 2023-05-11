package com.example.privacyprotection.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class File {
    @Id
    private Integer id;
    private String fileName;
    private Integer fileOwnerId;
    private String fileDescribe;
    private Integer fileState;
    private Integer fileType;
    private String filePath;
    private String fileHash;
    private Timestamp createTime;
}
