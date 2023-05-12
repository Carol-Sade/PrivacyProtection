package com.example.privacyprotection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privacyprotection.entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<File> {

    File getFileByFileName(String fileName);

}