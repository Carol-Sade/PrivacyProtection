package com.example.privacyprotection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privacyprotection.VO.ChaincodeVO;
import com.example.privacyprotection.entity.Chaincode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChaincodeMapper extends BaseMapper<Chaincode> {

    Chaincode getChaincodeByName(String chaincodeName);

    List<ChaincodeVO> getChaincode();
}
