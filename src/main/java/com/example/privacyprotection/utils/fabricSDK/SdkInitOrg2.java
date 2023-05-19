package com.example.privacyprotection.utils.fabricSDK;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;

import java.util.ArrayList;
import java.util.List;

import static com.example.privacyprotection.config.PathConf.*;

/**
 * Org2的所有功能
 */
public class SdkInitOrg2 {


    /**
     * 安装合约
     * 只有在安装合约的时候需要 org2网络参与  实例化的时候不需要它参与
     */
    public static Object install(String chaincodeVersion, String chaincodeLocation, String chaincodeName) throws Exception {
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org2");
        userContext.setMspId("Org2MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment = UserUtils.getEnrollment(org2KeyFolderPath, org2KeyFileName, org2CertFolderPath, org2CertFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer0 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer("peer1.org2.example.com", "grpcs://peer1.org2.example.com:10051", org2TlsPeerFilePath);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        return fabricClient.installChaincode(TransactionRequest.Type.GO_LANG, chaincodeName, chaincodeVersion,
                chaincodeLocation, chaincodeName, peers);
    }

}
