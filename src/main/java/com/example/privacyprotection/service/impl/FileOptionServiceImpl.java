package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.VO.OptionVO;
import com.example.privacyprotection.entity.FileOption;
import com.example.privacyprotection.mapper.FileOptionMapper;
import com.example.privacyprotection.service.FileOptionService;
import com.example.privacyprotection.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileOptionServiceImpl implements FileOptionService {

    @Autowired
    private FileOptionMapper fileOptionMapper;

    @Autowired
    private TimeFormat timeFormat;

    @Value("${upload.avatarUrl}")
    private String avatarUrl;

    public Integer userOption(Integer optionUserId, Integer fileId, String option) {
        FileOption fileOption = new FileOption();
        fileOption.setId(0);
        fileOption.setOptionUserId(optionUserId);
        fileOption.setFileId(fileId);
        fileOption.setOptionName(option);
        fileOption.setCreateTime(new Timestamp((System.currentTimeMillis())));
        return fileOptionMapper.insert(fileOption);
    }

    public Integer deleteOption(Integer optionId) {
        return fileOptionMapper.deleteById(optionId);
    }

    public List<OptionVO> getOptions(Integer userId) {
        List<OptionVO> optionList = fileOptionMapper.getOptions(userId);
        for (OptionVO optionVO : optionList) {
            optionVO.setAvatar(avatarUrl + optionVO.getAvatar());
            optionVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(optionVO.getCreateTime())));
        }
        return optionList;
    }

    /**
     * 获取管理员文件操作
     * @param userId
     * @return
     */
    public List<OptionVO> getAdminOptions(Integer userId) {
        List<OptionVO> optionList = fileOptionMapper.getOptions(userId);
        List<OptionVO> optionVOList = new ArrayList<>();
        for (OptionVO optionVO : optionList) {
            if (optionVO.getOptionName().equals("审核删除") || optionVO.getOptionName().equals("审核恢复")) {
                continue;
            }
            optionVO.setAvatar(avatarUrl + optionVO.getAvatar());
            optionVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(optionVO.getCreateTime())));
            optionVOList.add(optionVO);
        }
        return optionVOList;
    }

    /**
     * 获取管理员审核操作
     * @param userId
     * @return
     */
    public List<OptionVO> getExamineOptions(Integer userId) {
        List<OptionVO> optionList = fileOptionMapper.getExamineOptions(userId);
        List<OptionVO> optionVOList = new ArrayList<>();
        for (OptionVO optionVO : optionList) {
            if (optionVO.getOptionName().equals("审核删除") || optionVO.getOptionName().equals("审核恢复")) {
                optionVO.setAvatar(avatarUrl + optionVO.getAvatar());
                optionVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(optionVO.getCreateTime())));
                optionVOList.add(optionVO);
            }
        }
        return optionVOList;
    }

}
