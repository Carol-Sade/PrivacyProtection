package com.example.privacyprotection.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyFileVO {
    private Integer id;
    private String fileName;
    private String fileDescribe;
    private Integer fileState;
    private Integer fileType;
    List<CommentVO> comments = new ArrayList<>();
    private String createTime;
}
