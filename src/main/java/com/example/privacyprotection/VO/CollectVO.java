package com.example.privacyprotection.VO;

import lombok.Data;

@Data
public class CollectVO {
    private Integer id;
    private String username;
    private String avatar;
    private Integer fileId;
    private String fileName;
    private String fileDescribe;
    private Integer fileType;
    private String createTime;
}
