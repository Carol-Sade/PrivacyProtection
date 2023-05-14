package com.example.privacyprotection.service;

import com.example.privacyprotection.VO.OptionVO;

import java.util.List;

public interface FileOptionService {
    Integer userOption(Integer optionUserId, Integer fileId, String option);

    Integer deleteOption(Integer optionId);

    List<OptionVO> getOptions(Integer userId);

    List<OptionVO> getAdminOptions(Integer userId);

    List<OptionVO> getExamineOptions(Integer userId);

}
