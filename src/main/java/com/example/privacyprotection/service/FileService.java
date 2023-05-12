package com.example.privacyprotection.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Integer uploadFile(Integer userId, MultipartFile multipartFile, String describe, Integer type) throws Exception;

    String downloadFile(Integer fileId);

    Integer share(Integer userId, Integer fileId);

    Integer deleteFile(Integer userId, Integer fileId);

    Integer cancelShare(Integer userId, Integer fileId);


}
