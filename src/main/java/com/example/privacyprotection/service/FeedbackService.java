package com.example.privacyprotection.service;

import com.example.privacyprotection.VO.FeedbackVO;
import com.example.privacyprotection.entity.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackService {
    Integer feedback(Integer userId, String content);

    List<FeedbackVO> getFeedbacks();

    Integer deleteFeedback(Integer feedbackId);
}
