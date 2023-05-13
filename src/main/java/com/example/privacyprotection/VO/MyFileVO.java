package com.example.privacyprotection.VO;

import lombok.Data;

@Data
public class MyFileVO {
    private Integer id;
    private String fileName;
    private String fileDescribe;
    private Integer fileState;
    private Integer fileType;
    private String createTime;
}
