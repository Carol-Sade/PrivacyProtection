package com.example.privacyprotection.VO;

import lombok.Data;

@Data
public class FeedbackVO {
    private Integer id;
    private String username;
    private String avatar;
    private String content;
    private String time;
}
