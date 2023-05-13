package com.example.privacyprotection.VO;

import lombok.Data;

@Data
public class ShareVO {
    private Integer id;
    private String fileName;
    private String fileDescribe;
    private Integer type;
    private String username;
    private String avatar;
    private String createTime;
}
