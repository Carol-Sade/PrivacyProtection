package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.entity.File;
import com.example.privacyprotection.mapper.FileMapper;
import com.example.privacyprotection.service.FileService;
import com.example.privacyprotection.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UploadFile uploadFile;

    public Integer uploadFile(Integer userId, MultipartFile multipartFile, String describe, Integer type) throws Exception {
        File file = new File();
        file.setId(0);
        file.setUserId(userId);
        file.setFileType(type);
        file.setFileDescribe(describe);
        file.setFileState(0);
        file.setFileName(multipartFile.getOriginalFilename());
        file.setCreateTime(new Timestamp((System.currentTimeMillis())));
        String filePath = uploadFile.uploadFile(multipartFile);
        file.setFilePath(filePath);
        file.setFileHash(filePath.substring(0, filePath.indexOf(".")));
        File check = fileMapper.getFileByFileName(file.getFilePath());
        if (check != null) {
            return -2;
        } else {
            return fileMapper.insert(file);
        }
    }
}
