package com.example.privacyprotection.controller;

import com.example.privacyprotection.service.FileCommentService;
import com.example.privacyprotection.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fileComment")
public class FileCommentController {

    @Autowired
    private FileCommentService fileCommentService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/comment")
    public Map<String, Object> comment(@RequestHeader String token,
                                       @RequestParam Integer fileId,
                                       @RequestParam String content) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = fileCommentService.comment(userId, fileId, content);
            if (code == 1) {
                map.put("code", 1);
                map.put("msg", "success");
            } else {
                map.put("code", 0);
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/getFileComments")
    public Map<String, Object> getFileComments(@RequestHeader String token,
                                               @RequestParam Integer fileId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            map.put("list", fileCommentService.getFileComments(fileId));
            map.put("code", 1);
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/deleteComment")
    public Map<String, Object> deleteComment(@RequestHeader String token,
                                             @RequestParam Integer commentId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = fileCommentService.deleteComment(commentId);
            if (code == 1) {
                map.put("code", 1);
                map.put("msg", "success");
            } else {
                map.put("code", 0);
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}

