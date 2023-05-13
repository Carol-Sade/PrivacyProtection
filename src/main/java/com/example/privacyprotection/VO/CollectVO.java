package com.example.privacyprotection.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CollectVO {
    private Integer id;
    private String username;
    private String avatar;
    private Integer fileId;
    private String fileName;
    private String fileDescribe;
    private Integer fileType;
    List<CommentVO> comments = new ArrayList<>();
    private String createTime;
}
