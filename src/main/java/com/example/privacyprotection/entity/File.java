package com.example.privacyprotection.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class File {
    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String fileName;
    private Integer userId;
    private String fileDescribe;
    private Integer fileState;
    private Integer fileType;
    private String filePath;
    private String fileHash;
    private Timestamp createTime;
}
