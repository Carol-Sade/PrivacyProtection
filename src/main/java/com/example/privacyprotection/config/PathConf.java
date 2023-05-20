package com.example.privacyprotection.config;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * <h3>FileManage</h3>
 * <p>包含所有绝对路径 以便以后修改</p>
 *
 * @author : sugar without coffee
 * @date : 2020-04-11 12:38
 **/
public class PathConf {

    /**
     * 需要修改的内容，只需要修改秘钥，其他不需要修改
     * org1KeyFileName:变量值为文件名，文件位于"resources/file/fabric/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore"目录下
     * org2KeyFileName:变量值为文件名，文件位于"resources/file/fabric/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/keystore"目录下
     */
    public static final String org1KeyFileName = "cf217e1fcd9b50f49757c6cde8e83b0684a0ca753ebd8d28e7b1099b68a3379d_sk";
    public static final String org2KeyFileName = "4167bb6d41212654259907d4e600399d7a4062781bd5b33afdf1edca554e81bf_sk";


    /**
     * Fabric公用路径
     */
    // 本地
    // public static final String DIR = "/Users/parsifal/Downloads/PrivacyProtection/PrivacyProtection/src/main/resources/crypto-config/";
    // 部署到服务器
    public static final String DIR = "/home/privacy/crypto-config/";
    public static final String tlsOrderFilePath = DIR + "ordererOrganizations" + File.separator + "example.com" + File.separator + "tlsca" + File.separator + "tlsca.example.com-cert.pem";

    /**
     * 以下是所有从ubuntu copy下来的文件位置和文件名
     * 用于配置SdkInitOrg1
     */
    public static final String org1KeyFolderPath = DIR + "peerOrganizations" + File.separator + "org1.example.com" + File.separator + "users" + File.separator + "Admin@org1.example.com" + File.separator + "msp" + File.separator + "keystore";
    public static final String org1CertFolderPath = DIR + "peerOrganizations" + File.separator + "org1.example.com" + File.separator + "users" + File.separator + "Admin@org1.example.com" + File.separator + "msp" + File.separator + "admincerts";
    public static final String org1CertFileName = "Admin@org1.example.com-cert.pem";
    public static final String org1TlsPeerFilePath = DIR + "peerOrganizations" + File.separator + "org1.example.com" + File.separator + "peers" + File.separator + "peer0.org1.example.com" + File.separator + "msp" + File.separator + "tlscacerts" + File.separator + "tlsca.org1.example.com-cert.pem";


    /**
     * 以下是所有从ubuntu copy下来的文件位置和文件名
     * 用于配置SdkInitOrg2
     */
    public static final String org2KeyFolderPath = DIR + "peerOrganizations" + File.separator + "org2.example.com" + File.separator + "users" + File.separator + "Admin@org2.example.com" + File.separator + "msp" + File.separator + "keystore";
    public static final String org2CertFolderPath = DIR + "peerOrganizations" + File.separator + "org2.example.com" + File.separator + "users" + File.separator + "Admin@org2.example.com" + File.separator + "msp" + File.separator + "admincerts";
    public static final String org2CertFileName = "Admin@org2.example.com-cert.pem";
    public static final String org2TlsPeerFilePath = DIR + "peerOrganizations" + File.separator + "org2.example.com" + File.separator + "peers" + File.separator + "peer0.org2.example.com" + File.separator + "msp" + File.separator + "tlscacerts" + File.separator + "tlsca.org2.example.com-cert.pem";
    /**
     * Fabric升级需要的文件
     */
    public static final String fabricUpdateFIle = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "file" + File.separator + "fabric" + File.separator + "config" + File.separator + "chaincodeendorsementpolicy.yaml";

    /**
     * 链码存放地址
     */

    public static final String chaincodeLocation = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "file" + File.separator + "chaincode";

}
