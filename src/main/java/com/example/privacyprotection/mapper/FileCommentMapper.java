package com.example.privacyprotection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privacyprotection.VO.CollectVO;
import com.example.privacyprotection.VO.CommentVO;
import com.example.privacyprotection.entity.FileComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileCommentMapper extends BaseMapper<FileComment> {

    List<CommentVO> getFileComments(Integer fileId);
}
