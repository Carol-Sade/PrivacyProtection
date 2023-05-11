package com.example.privacyprotection.controller;

import com.example.privacyprotection.utils.fabricSDK.FabricClient;
import com.example.privacyprotection.utils.fabricSDK.UserContext;
import com.example.privacyprotection.utils.fabricSDK.UserUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static com.example.privacyprotection.config.PathConf.*;

@RestController
@RequestMapping("api/chaincode")
public class ChaincodeController {

    String channelName = "mychannel";
    String chainCodeName = "file";

    @Test
    public void invoke() {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("adminController");
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
            String[] initArgs = {"[\"save\",\"5\",\"{\"UserId\":\"5\",\"path\":\"yyy/xxxx\",\"file\":\"filena\"}\"]\""};
            fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG, chainCodeName, order, peers, "save", initArgs);
        } catch (ProposalException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (org.bouncycastle.crypto.CryptoException e) {
            throw new RuntimeException(e);
        } catch (CryptoException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }


//    @Test
//    public void query() throws InvalidArgumentException, CryptoException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
//        try {
//            UserContext userContext = new UserContext();
//            userContext.setAffiliation("Org1");
//            userContext.setMspId("Org1MSP");
//            userContext.setAccount("李伟");
//            userContext.setName("adminController");
//            Enrollment enrollment = null;
//            enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFolderPath, org1CertFileName);
//            userContext.setEnrollment(enrollment);
//            FabricClient fabricClient = new FabricClient(userContext);
//            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
//            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
//            List<Peer> peers = new ArrayList<>();
//            peers.add(peer0);
//            peers.add(peer1);
//            String[] initArgs = {"1"};
//            Map map = fabricClient.queryChaincode(peers, channelName, TransactionRequest.Type.GO_LANG, chainCodeName, "query", initArgs);
//            System.out.println(map);
//        } catch (TransactionException e) {
//            throw new RuntimeException(e);
//        } catch (ProposalException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        } catch (org.bouncycastle.crypto.CryptoException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void xxx() throws InvalidArgumentException, CryptoException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, org.bouncycastle.crypto.CryptoException, TransactionException, ProposalException {
//        String keyFolderPath = "/Users/parsifal/Downloads/fabric实战/fabric/src/main/resources/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/keystore";
//        String keyFileName = "545f8666fa88e19dcffc109765c38d9ffe84c4d6a793761ba9518de3ce012597_sk";
//        String certFoldePath = "/Users/parsifal/Downloads/fabric实战/fabric/src/main/resources/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/admincerts";
//        String certFileName = "Admin@org2.example.com-cert.pem";
//        String tlsOrderFilePath = "/Users/parsifal/Downloads/fabric实战/fabric/src/main/resources/crypto-config/peerOrganizations/org1.example.com/tlsca/tlsca.org1.example.com-cert.pem";
//        String txfilePath = "E:\\fabric\\src\\main\\resources\\test.tx";
//        String tlsPeerFilePath = "/Users/parsifal/Downloads/fabric实战/fabric/src/main/resources/crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/msp/tlscacerts/tlsca.org1.example.com-cert.pem";
//        String tlsPeerFilePathAddtion = "/Users/parsifal/Downloads/fabric实战/fabric/src/main/resources/crypto-config/peerOrganizations/org2.example.com/tlsca/tlsca.org2.example.com-cert.pem";
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org2");
//        userContext.setMspId("Org2MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        Enrollment enrollment = UserUtils.getEnrollment(keyFolderPath, keyFileName, certFoldePath, certFileName);
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", tlsPeerFilePath);
//        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", tlsPeerFilePathAddtion);
//        List<Peer> peers = new ArrayList<>();
//        peers.add(peer0);
//        peers.add(peer1);
//        Orderer order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);
//        String initArgs[] = {"110114", "{\"name\":\"zhangsan\",\"identity\":\"110114\",\"mobile\":\"18910012222\"}"};
//        fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG, "basicinfo", order, peers, "save", initArgs);
//    }
}
