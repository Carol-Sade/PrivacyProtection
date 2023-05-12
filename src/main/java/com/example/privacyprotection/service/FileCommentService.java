package com.example.privacyprotection.service;

import com.example.privacyprotection.VO.CommentVO;

import java.util.List;

public interface FileCommentService {

    Integer comment(Integer userId, Integer fileId, String content);

    List<CommentVO> getFileComments(Integer fileId);

    Integer deleteComment(Integer commentId);
}
