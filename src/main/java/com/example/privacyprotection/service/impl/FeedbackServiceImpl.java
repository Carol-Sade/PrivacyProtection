package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.VO.FeedbackVO;
import com.example.privacyprotection.entity.Feedback;
import com.example.privacyprotection.entity.User;
import com.example.privacyprotection.mapper.FeedbackMapper;
import com.example.privacyprotection.mapper.UserMapper;
import com.example.privacyprotection.service.FeedbackService;
import com.example.privacyprotection.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private TimeFormat timeFormat;

    @Value("${upload.avatarUrl}")
    private String avatarUrl;

    public Integer feedback(Integer userId, String content) {
        Feedback feedback = new Feedback();
        feedback.setId(0);
        feedback.setUserId(userId);
        feedback.setContent(content);
        feedback.setCreateTime(new Timestamp((System.currentTimeMillis())));
        try {
            feedbackMapper.insert(feedback);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public List<FeedbackVO> getFeedbacks() {
        List<Feedback> feedbakcList = feedbackMapper.selectList(null);
        List<FeedbackVO> feedbackVOList = new ArrayList<>();
        for (Feedback feedback : feedbakcList) {
            FeedbackVO feedbackVO = new FeedbackVO();
            int userId = feedback.getUserId();
            feedbackVO.setId(feedback.getId());
            User user = userMapper.selectById(userId);
            feedbackVO.setUsername(user.getUsername());
            feedbackVO.setAvatar(avatarUrl + user.getAvatar());
            feedbackVO.setContent(feedback.getContent());
            feedbackVO.setTime(timeFormat.formatYMDHMS(feedback.getCreateTime()));
            feedbackVOList.add(feedbackVO);
        }
        return feedbackVOList;
    }

    public Integer deleteFeedback(Integer feedbackId) {
        return feedbackMapper.deleteById(feedbackId);
    }
}
