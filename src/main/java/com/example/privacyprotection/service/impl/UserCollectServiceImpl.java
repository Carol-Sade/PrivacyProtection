package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.VO.CollectVO;
import com.example.privacyprotection.VO.FileVO;
import com.example.privacyprotection.entity.UserCollect;
import com.example.privacyprotection.mapper.UserCollectMapper;
import com.example.privacyprotection.service.UserCollectService;
import com.example.privacyprotection.utils.TimeFormat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserCollectServiceImpl implements UserCollectService {

    @Autowired
    private UserCollectMapper userCollectMapper;

    @Autowired
    private TimeFormat timeFormat;

    public Integer collect(Integer userId, Integer fileId) {
        UserCollect check = userCollectMapper.checkCollected(userId, fileId);
        if (check != null) {
            return -2;
        }
        UserCollect userCollect = new UserCollect();
        userCollect.setId(0);
        userCollect.setUserId(userId);
        userCollect.setFileId(fileId);
        userCollect.setCreateTime(new Timestamp((System.currentTimeMillis())));
        return userCollectMapper.insert(userCollect);
    }

    public List<CollectVO> getUserCollections(Integer userId) {
        List<CollectVO> userCollectList = userCollectMapper.getUserCollections(userId);
        for (CollectVO collectVO : userCollectList) {
            collectVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(collectVO.getCreateTime())));
        }
        return userCollectList;
    }

    public Integer cancelCollect(Integer collectId) {
        return userCollectMapper.deleteById(collectId);
    }

    @Test
    public void test() {
        userCollectMapper.getUserCollections(10);
    }
}
