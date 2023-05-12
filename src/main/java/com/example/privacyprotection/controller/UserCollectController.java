package com.example.privacyprotection.controller;

import com.example.privacyprotection.service.UserCollectService;
import com.example.privacyprotection.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/userCollect")
public class UserCollectController {

    @Autowired
    private UserCollectService userCollectService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/collect")
    public Map<String, Object> collect(@RequestHeader String token,
                                       @RequestParam Integer fileId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = userCollectService.collect(userId, fileId);
            if (code == 1) {
                map.put("code", 1);
                map.put("msg", "success");
            } else if (code == -2) {
                map.put("code", -2);
                map.put("msg", "has collected");
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

    @GetMapping("/getUserCollections")
    public Map<String, Object> getUserCollections(@RequestHeader String token) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            map.put("list", userCollectService.getUserCollections(userId));
            map.put("code", 1);
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/cancelCollect")
    public Map<String, Object> cancelCollect(@RequestHeader String token,
                                             @RequestParam Integer collectId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = userCollectService.cancelCollect(collectId);
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
