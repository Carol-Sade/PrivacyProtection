package com.example.privacyprotection.service;

import com.example.privacyprotection.VO.MyFileVO;
import com.example.privacyprotection.VO.ShareVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    Integer uploadFile(Integer userId, MultipartFile multipartFile, String describe, Integer type) throws Exception;

    String downloadFile(Integer fileId);

    String downloadUserFile(Integer fileId);

    Integer share(Integer userId, Integer fileId);

    Integer deleteFile(Integer userId, Integer fileId);

    Integer cancelShare(Integer userId, Integer fileId);

    Integer restore(Integer userId, Integer fileId);

    Integer deleteAbsolutely(Integer userId, Integer fileId);

    Integer examineDelete(Integer userId, Integer fileId);

    Integer examineRestore(Integer userId, Integer fileId);

    List<MyFileVO> getMyFiles(Integer userId);

    List<ShareVO> getShare();

    List<ShareVO> getExamine();

    List<ShareVO> searchShare(String key);

}
