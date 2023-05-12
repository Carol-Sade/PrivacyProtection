package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.VO.CommentVO;
import com.example.privacyprotection.entity.FileComment;
import com.example.privacyprotection.mapper.FileCommentMapper;
import com.example.privacyprotection.service.FileCommentService;
import com.example.privacyprotection.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FileCommentServiceImpl implements FileCommentService {

    @Autowired
    private FileCommentMapper fileCommentMapper;

    @Autowired
    private TimeFormat timeFormat;
    public Integer comment(Integer userId, Integer fileId, String content) {
        FileComment fileComment = new FileComment();
        fileComment.setId(0);
        fileComment.setUserId(userId);
        fileComment.setFileId(fileId);
        fileComment.setContent(content);
        fileComment.setCreateTime(new Timestamp((System.currentTimeMillis())));
        return fileCommentMapper.insert(fileComment);
    }

    public List<CommentVO> getFileComments(Integer fileId) {
        List<CommentVO> commentVOList = fileCommentMapper.getFileComments(fileId);
        for(CommentVO commentVO:commentVOList){
            commentVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(commentVO.getCreateTime())));
        }
        return commentVOList;
    }

    public Integer deleteComment(Integer commentId) {
        return fileCommentMapper.deleteById(commentId);
    }
}
