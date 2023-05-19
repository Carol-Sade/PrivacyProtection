package com.example.privacyprotection.VO;

import lombok.Data;


@Data
public class ChaincodeVO {
    private Integer id;
    private String chaincodeName;
    private String chainUrl;
    private Integer chaincodeState;
    private String version;
    private String createTime;
}
