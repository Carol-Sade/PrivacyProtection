package com.example.privacyprotection.service;

import com.example.privacyprotection.VO.CollectVO;

import java.util.List;

public interface UserCollectService {

    Integer collect(Integer userId, Integer fileId);

    List<CollectVO> getUserCollections(Integer userId);

    Integer cancelCollect(Integer collectId);

}
