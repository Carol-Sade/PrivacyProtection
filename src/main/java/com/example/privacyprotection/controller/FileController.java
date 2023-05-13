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

    @GetMapping("/downloadFile")
    public Map<String, Object> downloadFile(@RequestHeader String token,
                                            @RequestParam Integer fileId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        String url = fileService.downloadFile(fileId);
        if (url != null) {
            map.put("code", 1);
            map.put("msg", "success");
            map.put("url", url);
        } else {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("/share")
    public Map<String, Object> share(@RequestHeader String token,
                                     @RequestParam Integer fileId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = fileService.share(userId, fileId);
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

    @GetMapping("/cancelShare")
    public Map<String, Object> cancelShare(@RequestHeader String token,
                                           @RequestParam Integer fileId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = fileService.cancelShare(userId, fileId);
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

    @GetMapping("/deleteFile")
    public Map<String, Object> deleteFile(@RequestHeader String token,
                                          @RequestParam Integer fileId) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            int code = fileService.deleteFile(userId, fileId);
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

    @GetMapping("/examineDelete")
    public Map<String, Object> examineDelete(@RequestHeader String token,
                                             @RequestParam Integer fileId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> check = jwtUtils.checkPermission(token);
        if (!check.get("role").equals(2)) {
            map.put("code", -2);
            map.put("msg", "without permission");
            return map;
        }
        try {
            int code = fileService.examineDelete(fileId);
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

    @GetMapping("/getMyFiles")
    public Map<String, Object> getMyFiles(@RequestHeader String token) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            map.put("code", 1);
            map.put("msg", "success");
            map.put("list", fileService.getMyFiles(userId));
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/getShare")
    public Map<String, Object> getShare(@RequestHeader String token) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            map.put("code", 1);
            map.put("msg", "success");
            map.put("list", fileService.getShare());
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/searchShare")
    public Map<String, Object> searchShare(@RequestHeader String token,
                                           @RequestParam String key) {
        Map<String, Object> map = new HashMap<>();
        int userId = jwtUtils.checkToken(token);
        if (userId == -1) {
            map.put("code", -1);
            map.put("msg", "token error");
            return map;
        }
        try {
            map.put("code", 1);
            map.put("msg", "success");
            map.put("list", fileService.searchShare(key));
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
