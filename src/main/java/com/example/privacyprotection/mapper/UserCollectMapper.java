package com.example.privacyprotection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privacyprotection.VO.CollectVO;
import com.example.privacyprotection.entity.User;
import com.example.privacyprotection.entity.UserCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCollectMapper extends BaseMapper<UserCollect> {


    UserCollect checkCollected(Integer userId, Integer fileId);

    List<CollectVO> getUserCollections(Integer userId);
}
