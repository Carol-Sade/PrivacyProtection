package com.example.privacyprotection.controller;

import com.example.privacyprotection.service.FileService;
import com.example.privacyprotection.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/uploadFile")
    public Map<String, Object> uploadFile(@RequestHeader String token,
                                          @RequestParam MultipartFile file,
                                          @RequestParam String describe,
                                          @RequestParam Integer type) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = fileService.uploadFile(userId, file, describe, type);
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
}
