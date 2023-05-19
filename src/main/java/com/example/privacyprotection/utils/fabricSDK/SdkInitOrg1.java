package com.example.privacyprotection.utils.fabricSDK;


import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;

import java.util.ArrayList;
import java.util.List;

import static com.example.privacyprotection.config.PathConf.*;


/**
 * Org1的所有功能
 */
public class SdkInitOrg1 {

    /**
     * 安装合约
     */
    public static Object install(String chaincodeVersion, String chaincodeLocation, String chaincodeName) throws Exception {
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer("peer1.org1.example.com", "grpcs://peer1.org1.example.com:8051", org1TlsPeerFilePath);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        return fabricClient.installChaincode(TransactionRequest.Type.GO_LANG, chaincodeName, chaincodeVersion,
                chaincodeLocation, chaincodeName, peers);
    }
}
