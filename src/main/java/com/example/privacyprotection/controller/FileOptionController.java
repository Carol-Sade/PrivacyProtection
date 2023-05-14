package com.example.privacyprotection.controller;

import com.example.privacyprotection.service.FileOptionService;
import com.example.privacyprotection.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/fileOption")
public class FileOptionController {

    @Autowired
    private FileOptionService fileOptionService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/deleteOption")
    Map<String, Object> deleteOption(@RequestHeader String token,
                                     @RequestParam Integer optionId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = fileOptionService.deleteOption(optionId);
            if (code == 1) {
                map.put("code", 1);
                map.put("msg", "success");
            } else if (code == -2) {
                map.put("code", -2);
                map.put("msg", "file exist");
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

    @GetMapping("/getOptions")
    Map<String, Object> getOptions(@RequestHeader String token) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        Map<String, Object> check = jwtUtils.checkPermission(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            if (check.get("role").equals(2)) {
                map.put("code", 1);
                map.put("msg", "success");
                map.put("list", fileOptionService.getAdminOptions(userId));
            } else {
                map.put("code", 1);
                map.put("msg", "success");
                map.put("list", fileOptionService.getOptions(userId));
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/getExamineOptions")
    Map<String, Object> getExamineOptions(@RequestHeader String token) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        Map<String, Object> check = jwtUtils.checkPermission(token);
        if (!check.get("role").equals(2)) {
            map.put("code", -2);
            map.put("msg", "without permission");
            return map;
        }
        try {
            map.put("code", 1);
            map.put("msg", "success");
            map.put("list", fileOptionService.getExamineOptions(userId));
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
