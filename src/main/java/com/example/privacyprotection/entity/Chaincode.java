package com.example.privacyprotection.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Chaincode {

    @Id
    private Integer id;
    private String chaincodeName;
    private String chainUrl;
    private Integer chaincodeState;
    private String version;
    private Timestamp createTime;
}
