package com.example.privacyprotection.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShareVO {
    private Integer id;
    private String fileName;
    private String fileDescribe;
    private Integer type;
    private String username;
    private String avatar;
    List<CommentVO> comments = new ArrayList<>();
    private String createTime;
}
