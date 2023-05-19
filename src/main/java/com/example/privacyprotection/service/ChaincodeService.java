package com.example.privacyprotection.service;

import com.example.privacyprotection.VO.ChaincodeVO;

import java.util.List;

public interface ChaincodeService {

    Integer createChaincode(String chaincodeName, String chaincodeUrl);

    Integer deleteChaincode(Integer chaincodeId);

    Integer installChaincode(String chaincodeName,String version);

    Integer instantiateChaincode(String chaincodeName,String version);


    String downloadChaincode(Integer chaincodeId);

    List<ChaincodeVO> getChaincode();
}
