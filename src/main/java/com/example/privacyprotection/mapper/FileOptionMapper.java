package com.example.privacyprotection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privacyprotection.VO.OptionVO;
import com.example.privacyprotection.entity.FileOption;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileOptionMapper extends BaseMapper<FileOption> {

    List<OptionVO> getOptions(Integer userId);

}
