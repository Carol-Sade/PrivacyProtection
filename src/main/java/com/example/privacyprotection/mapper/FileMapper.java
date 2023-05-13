package com.example.privacyprotection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privacyprotection.VO.MyFileVO;
import com.example.privacyprotection.VO.ShareVO;
import com.example.privacyprotection.entity.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<File> {

    File getFileByFileName(String fileName);

    List<MyFileVO> getMyFiles(Integer userId);

    List<ShareVO> getShare();

    List<ShareVO> searchShare(String key);


}
