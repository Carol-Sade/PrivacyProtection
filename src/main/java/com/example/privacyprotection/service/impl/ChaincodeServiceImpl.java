package com.example.privacyprotection.service.impl;

import com.example.privacyprotection.VO.ChaincodeVO;
import com.example.privacyprotection.entity.Chaincode;
import com.example.privacyprotection.mapper.ChaincodeMapper;
import com.example.privacyprotection.service.ChaincodeService;
import com.example.privacyprotection.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ChaincodeServiceImpl implements ChaincodeService {

    @Autowired
    private ChaincodeMapper chaincodeMapper;

    @Autowired
    private TimeFormat timeFormat;

    @Value("${upload.chaincodeUrl}")
    private String chaincodeUrl;

    public Integer createChaincode(String chaincodeName, String chaincodeUrl) {
        Chaincode chaincode = new Chaincode();
        chaincode.setId(0);
        chaincode.setChaincodeName(chaincodeName);
        chaincode.setChaincodeState(0);
        chaincode.setVersion("1.0");
        chaincode.setChainUrl(chaincodeUrl);
        chaincode.setCreateTime(new Timestamp((System.currentTimeMillis())));
        return chaincodeMapper.insert(chaincode);
    }

    public Integer deleteChaincode(Integer chaincodeId) {
        return chaincodeMapper.deleteById(chaincodeId);
    }

    public Integer installChaincode(String chaincodeName, String version) {
        Chaincode chaincode = chaincodeMapper.getChaincodeByName(chaincodeName);
        chaincode.setChaincodeState(1);
        chaincode.setVersion(version);
        return chaincodeMapper.updateById(chaincode);
    }

    public Integer instantiateChaincode(String chaincodeName, String version) {
        Chaincode chaincode = chaincodeMapper.getChaincodeByName(chaincodeName);
        chaincode.setChaincodeState(2);
        chaincode.setVersion(version);
        return chaincodeMapper.updateById(chaincode);
    }


    public String downloadChaincode(Integer chaincodeId) {
        Chaincode chaincode = chaincodeMapper.selectById(chaincodeId);
        return chaincodeUrl + chaincode.getChaincodeName() + "/" + chaincode.getChainUrl();
    }

    public List<ChaincodeVO> getChaincode() {
        List<ChaincodeVO> chaincodeVOList = chaincodeMapper.getChaincode();
        for (ChaincodeVO chaincodeVO : chaincodeVOList) {
            chaincodeVO.setChainUrl(chaincodeUrl + chaincodeVO.getChaincodeName() + "/" + chaincodeVO.getChainUrl());
            chaincodeVO.setCreateTime(timeFormat.formatYMDHMS(Timestamp.valueOf(chaincodeVO.getCreateTime())));
        }
        return chaincodeVOList;
    }
}
