package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.VO.MyFileVO;
import com.example.privacyprotection.VO.ShareVO;
import com.example.privacyprotection.entity.File;
import com.example.privacyprotection.mapper.FileMapper;
import com.example.privacyprotection.service.FileService;
import com.example.privacyprotection.utils.TimeFormat;
import com.example.privacyprotection.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UploadFile uploadFile;

    @Autowired
    private TimeFormat timeFormat;


    @Value("${upload.url}")
    private String url;

    @Value("${upload.avatarUrl}")
    private String avatarUrl;

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

    public String downloadFile(Integer fileId) {
        File file = fileMapper.selectById(fileId);
        if (file != null && file.getFileState().equals(1)) {
            return url + file.getFilePath();
        } else {
            return null;
        }
    }

    public Integer share(Integer userId, Integer fileId) {
        File file = fileMapper.selectById(fileId);
        if (file != null && file.getUserId().equals(userId)) {
            file.setFileState(1);
            fileMapper.updateById(file);
            return 1;
        } else {
            return 0;
        }
    }

    public Integer deleteFile(Integer userId, Integer fileId) {
        File file = fileMapper.selectById(fileId);
        if (file != null && file.getUserId().equals(userId)) {
            file.setFileState(-1);
            fileMapper.updateById(file);
            return 1;
        } else {
            return 0;
        }
    }

    public Integer cancelShare(Integer userId, Integer fileId) {
        File file = fileMapper.selectById(fileId);
        if (file != null && file.getUserId().equals(userId)) {
            file.setFileState(0);
            fileMapper.updateById(file);
            return 1;
        } else {
            return 0;
        }
    }

    public Integer examineDelete(Integer fileId) {
        File file = fileMapper.selectById(fileId);
        if (file != null) {
            file.setFileState(-2);
            fileMapper.updateById(file);
            return 1;
        } else {
            return 0;
        }
    }

    public List<MyFileVO> getMyFiles(Integer userId) {
        List<MyFileVO> fileList = fileMapper.getMyFiles(userId);
        for (MyFileVO myFileVO : fileList) {
            myFileVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(myFileVO.getCreateTime())));
        }
        return fileList;
    }

    public List<ShareVO> getShare() {
        List<ShareVO> shareList = fileMapper.getShare();
        for (ShareVO shareVO : shareList) {
            shareVO.setAvatar(avatarUrl + shareVO.getAvatar());
            shareVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(shareVO.getCreateTime())));
        }
        return shareList;
    }

    public List<ShareVO> searchShare(String key) {
        List<ShareVO> shareList = fileMapper.searchShare(key);
        for (ShareVO shareVO : shareList) {
            shareVO.setAvatar(avatarUrl + shareVO.getAvatar());
            shareVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(shareVO.getCreateTime())));
        }
        return shareList;
    }
}
