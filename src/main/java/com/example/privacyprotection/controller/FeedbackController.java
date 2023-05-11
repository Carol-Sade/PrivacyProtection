package com.example.privacyprotection.controller;

import com.example.privacyprotection.service.FeedbackService;
import com.example.privacyprotection.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/feedback")
    public Map<String, Object> feedback(@RequestHeader String token,
                                        @RequestParam String content) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        int code = feedbackService.feedback(userId, content);
        if (code == 1) {
            map.put("code", 1);
            map.put("msg", "success");
        } else {
            map.put("code", 0);
            map.put("msg", "file");
        }
        return map;
    }

    @GetMapping("/getFeedbacks")
    public Map<String, Object> getFeedbacks(@RequestHeader String token) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> check = jwtUtils.checkPermission(token);
        if (!check.get("role").equals(2)) {
            map.put("code", -2);
            map.put("msg", "without permission");
            return map;
        }
        map.put("code", 1);
        map.put("msg", "success");
        map.put("list", feedbackService.getFeedbacks());
        return map;
    }

    @GetMapping("/deleteFeedback")
    public Map<String, Object> deleteFeedback(@RequestHeader String token,
                                              @RequestParam Integer feedbackId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> check = jwtUtils.checkPermission(token);
        if (!check.get("role").equals(2)) {
            map.put("code", -2);
            map.put("msg", "without permission");
            return map;
        }
        int code = feedbackService.deleteFeedback(feedbackId);
        map.put("code", code);
        if (code == 1) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }
}
