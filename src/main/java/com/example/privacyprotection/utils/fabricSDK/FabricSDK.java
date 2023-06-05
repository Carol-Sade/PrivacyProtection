package com.example.privacyprotection.utils.fabricSDK;


import org.hyperledger.fabric.sdk.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.example.privacyprotection.config.PathConf.*;


/**
 * FabricSDK：在以后使用的时候直接实例化即可
 */
public class FabricSDK {

    /**
     * chainCodeName ： 合约名称
     */
    private String chainCodeName;
    private String channelname;

    public FabricSDK() {
    }

    public FabricSDK(String chainCodeName, String channelname) {
        this.chainCodeName = chainCodeName;
        this.channelname = channelname;
    }

    /**
     * 安装chaincode
     */
    public Object installChaincode(String chaincodeVersion, String chaincodeLocation, String chaincodeName) throws Exception {
        String installOrg1 = SdkInitOrg1.install(chaincodeVersion, chaincodeLocation, chaincodeName).toString();
        String installOrg2 = SdkInitOrg2.install(chaincodeVersion, chaincodeLocation, chaincodeName).toString();
        if ("true".equals(installOrg1) && "true".equals(installOrg2)) {
            return "success";
        } else {
            return installOrg1 + "\n" + installOrg2;
        }
    }

    /**
     * 合约实例化
     */
    public Object instantiated(String chaincodeName, String chaincodeVersion) throws Exception {
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
        Orderer order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);

//            UserContext userContext = new UserContext();
//            userContext.setAffiliation("Org2");
//            userContext.setMspId("Org2MSP");
//            userContext.setAccount("李伟");
//            userContext.setName("admin");
//            Enrollment enrollment = UserUtils.getEnrollment(org2KeyFolderPath, org2KeyFileName, org2CertFolderPath, org2CertFileName);
//            userContext.setEnrollment(enrollment);
//            FabricClient fabricClient = new FabricClient(userContext);
//            Peer peer = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
//            Orderer order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);

        String[] initArgs = {""};
        return fabricClient.initChaincode(channelname, TransactionRequest.Type.GO_LANG, chaincodeName, chaincodeVersion, order, peer, "init", initArgs);
    }

    /**
     * 合约升级(出现错误：ERROR [cn.com.fabric.sdk.FabricClient] cannot get package for chaincode (admin:2.0) upgrade fail
     */
    public Object upgradeChaincode(String chaincodeName, String chaincodeVersion) throws Exception{
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
        Orderer order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);
        String[] initArgs = {""};
        return fabricClient.upgradeChaincode(channelname, TransactionRequest.Type.GO_LANG, chaincodeName, chaincodeVersion, order, peer, "init", initArgs);
    }

    /**
     * invoke 合约
     * invoke(initArgsInvokeRecord,chaincodeName4);
     *
     * @return Object
     */
    public boolean invoke(String[] initArgs) {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("admin");
            Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
            List<Peer> peers = new ArrayList<>();
            peers.add(peer0);
            peers.add(peer1);
            Orderer order = null;
            order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);

            fabricClient.invoke(channelname, TransactionRequest.Type.GO_LANG, chainCodeName, order, peers, "save", initArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询最新合约
     * String[] initArgsQuery1 = {"10001"};
     * queryAllChaincode(initArgsQuery10003,chaincodeName3)
     */
    public Map queryChaincode(String[] initArgs) {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("adminController");
            Enrollment enrollment = null;
            enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
            List<Peer> peers = new ArrayList<>();
            peers.add(peer0);
            peers.add(peer1);
            Map map = fabricClient.queryChaincode(peers, channelname, TransactionRequest.Type.GO_LANG, chainCodeName, "query", initArgs);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有合约
     * queryAllChaincode(initArgsQuery10003,chaincodeName4)
     *
     * @return
     */
    public Collection queryAllChaincode(String[] initArgs) {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("adminController");
            Enrollment enrollment = null;
            enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
            List<Peer> peers = new ArrayList<>();
            peers.add(peer0);
            peers.add(peer1);
            Map map = fabricClient.queryChaincode(peers, channelname, TransactionRequest.Type.GO_LANG, chainCodeName, "queryHistory", initArgs);
            return map.values();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据channelName获取channel
     *
     * @param channelName
     * @return
     */
    public Channel getChannel(String channelName) {
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("adminController");
        Channel channel = null;
        Enrollment enrollment = null;
        try {
            enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            channel = fabricClient.getChannel(channelName);
            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
            List<Peer> peers = new ArrayList<>();
            peers.add(peer0);
            peers.add(peer1);
            for (Peer p : peers) {
                channel.addPeer(p);
            }
            channel.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }

    public FabricClient getClient() {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org2");
            userContext.setMspId("Org2MSP");
            userContext.setAccount("李伟");
            userContext.setName("admin");
            Enrollment enrollment = UserUtils.getEnrollment(org2KeyFolderPath, org2KeyFileName, org2CertFolderPath, org2CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            return fabricClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
