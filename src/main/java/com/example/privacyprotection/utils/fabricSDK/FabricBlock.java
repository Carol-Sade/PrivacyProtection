//package com.example.privacyprotection.utils.fabricSDK;
//
//
//import com.google.protobuf.InvalidProtocolBufferException;
//import org.apache.commons.codec.binary.Hex;
//import org.hyperledger.fabric.protos.common.Common;
//import org.hyperledger.fabric.protos.peer.Chaincode;
//import org.hyperledger.fabric.protos.peer.FabricTransaction;
//import org.hyperledger.fabric.protos.peer.ProposalPackage;
//import org.hyperledger.fabric.protos.peer.TransactionPackage;
//import org.hyperledger.fabric.sdk.*;
//import org.hyperledger.fabric.sdk.exception.CryptoException;
//import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
//import org.hyperledger.fabric.sdk.exception.ProposalException;
//import org.hyperledger.fabric.sdk.exception.TransactionException;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.tjut.dcs.digitalcollectionsys.config.PathConf.*;
//
///**
// * @author WPPStart
// * @PROJECT_NAME:sdkdemo
// * @create
// * @date:2022-05-24-14:41
// * @DESCRIPTION:
// */
//public class FabricBlock {
//    /**
//     * channelName ： 区块链名称
//     */
//    private String channelName;
//
//    public FabricBlock(String channelName) {
//        this.channelName = channelName;
//    }
//
//    public FabricBlock() {
//    }
////    public static void main(String[] args) throws InvalidArgumentException, ProposalException, TransactionException {
////        FabricSDK fabricSDK = new FabricSDK("ArtWorkInfo");
////        Channel channel = fabricSDK.getChannel("mychannel");
////
////        channel.initialize();
////        BlockchainInfo blockchainInfo = channel.queryBlockchainInfo();
////
////        byte[] hash = blockchainInfo.getCurrentBlockHash();
////        System.out.println("Hex.encodeHexString(hash) = " + Hex.encodeHexString(hash));
////        BlockInfo returnedBlock = channel.queryBlockByHash(hash);// 根据Hash获得
////// 获取Hash
////// 数据的Hash
////        String dataHash = Hex.encodeHexString(returnedBlock.getDataHash());
////// 前一个区块的Hash
////        String PreviousHash = Hex.encodeHexString(returnedBlock.getPreviousHash());
////        System.out.println("dataHash = " + dataHash);
////        System.out.println("PreviousHash = " + PreviousHash);
////// 计算出当前区块的Hash
////// *************BlockHash = cal(blockNumber,block.preHash,DataHash)*****************
//////        Hex.encodeHexString(SDKUtils.calculateBlockHash(client,
//////                  blockNumber, returnedBlock.getPreviousHash(), returnedBlock.getDataHash()));
////    }
//
//    /**
//     * 获取当前的区块的哈希值
//     */
//    public String getCurrBlockHash() {
//        FabricSDK fabricSDK = new FabricSDK();
//        Channel channel = fabricSDK.getChannel(channelName);
//
//        BlockchainInfo blockchainInfo = null;
//        try {
//            channel.initialize();
//            blockchainInfo = channel.queryBlockchainInfo();
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        } catch (TransactionException e) {
//            e.printStackTrace();
//        } catch (ProposalException e) {
//            e.printStackTrace();
//        }
//
//        // 获取到当前区块的hash
//        byte[] hash = blockchainInfo.getCurrentBlockHash();
//        return Hex.encodeHexString(hash);
//    }
//
//    /**
//     * 根据区块号获取Block
//     *
//     * @param BlockNumber
//     * @return
//     */
//    public BlockInfo getBlockByNumber(long BlockNumber) {
//        FabricSDK fabricSDK = new FabricSDK();
//        Channel channel = fabricSDK.getChannel(channelName);
//        BlockInfo returnedBlock = null;
//
//        try {
//            channel.initialize();
//            // 根据区块号获取
//            returnedBlock = channel.queryBlockByNumber(BlockNumber);
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        } catch (ProposalException e) {
//            e.printStackTrace();
//        } catch (TransactionException e) {
//            e.printStackTrace();
//        }
//        //返回根据BlockNumber的区块
//        return returnedBlock;
//    }
//
//    public long getBlockHeight() {
//        FabricSDK fabricSDK = new FabricSDK();
//        Channel channel = fabricSDK.getChannel(channelName);
//        long height = 0;
//        try {
//            height = channel.queryBlockchainInfo().getHeight();
//        } catch (ProposalException e) {
//            e.printStackTrace();
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        }
//
//        return height;
//    }
//
//    /**
//     * 根据blockNum获取区块的哈希值
//     *
//     * @param blockNum
//     * @return
//     */
//    public String getDataHashByBlockNum(long blockNum) {
//        BlockInfo blockByNumber = this.getBlockByNumber(blockNum);
//        byte[] dataHash = blockByNumber.getDataHash();
//        return Hex.encodeHexString(dataHash);
//    }
//
//    /**
//     * 计算区块和数据的哈希值  并返回
//     *
//     * @param blockInfo
//     * @return
//     */
//    public String getcalculateBlockHash(BlockInfo blockInfo) {
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org1");
//        userContext.setMspId("Org1MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        FabricClient fabricClient = null;
//        String OnlyHash = null;
//        try {
//            Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFoldePath, org1CertFileName);
//            userContext.setEnrollment(enrollment);
//
//            fabricClient = new FabricClient(userContext);
//            // 计算出当前区块的Hash
//            long blockNumber = blockInfo.getBlockNumber();
//            OnlyHash = Hex.encodeHexString(SDKUtils.calculateBlockHash(fabricClient.getHfClient(),
//                    blockNumber, blockInfo.getPreviousHash(), blockInfo.getDataHash()));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (CryptoException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        } catch (org.bouncycastle.crypto.CryptoException e) {
//            e.printStackTrace();
//        }
//
//        return OnlyHash;
//    }
//
//    /**
//     * 根据txId来查询块上信息
//     *
//     * @param TransactionByID
//     * @throws InvalidArgumentException
//     * @throws ProposalException
//     * @throws InvalidProtocolBufferException
//     */
//    public String parseDataByTransactionByID(String TransactionByID) throws InvalidArgumentException, ProposalException, InvalidProtocolBufferException {
//        FabricSDK fabricSDK = new FabricSDK();
//        Channel channel = fabricSDK.getChannel(channelName);
//        //根据Txid获取到transactionInfo
//        TransactionInfo transactionInfo = channel.queryTransactionByID(TransactionByID);
//        //transaction is stored inside the envelope containing the payload and signature  交易被存储在envelope里面
//        Common.Envelope envelope = transactionInfo.getEnvelope();
//
//        Common.Payload payload = Common.Payload.parseFrom(envelope.getPayload());
//        // payload contains Header and Data. We are parsing data to get the transaction  解析data
//        FabricTransaction.Transaction transaction = FabricTransaction.Transaction.parseFrom(payload.getData());
//
//        System.out.println("transaction.toString() = " + transaction.toString());
//
//        // get first action from the transaction action list. it contains input and other details
//        FabricTransaction.TransactionAction action = transaction.getActionsList().get(0); // 0 is a index
//        // chaincode action payload contains input parameters. So we are taking the action payload
//        TransactionPackage.ChaincodeActionPayload chaincodeActionPayload = TransactionPackage.ChaincodeActionPayload.parseFrom(action.getPayload());
//        // chaincode ProposalPayload contains Input and TransientMap. We are parsing actionPayload to proposalPayload
//        ProposalPackage.ChaincodeProposalPayload prp = ProposalPackage.ChaincodeProposalPayload.parseFrom(chaincodeActionPayload.getChaincodeProposalPayload());
//        // parse the input to chaincodeInvocationSpec so that we can unmarshal the input
//        Chaincode.ChaincodeInvocationSpec chaincodeInvocationSpec = Chaincode.ChaincodeInvocationSpec.parseFrom(prp.getInput());
//
//        // get the input and parse the arg list and get input arguments
//        String s = chaincodeInvocationSpec.getChaincodeSpec().getInput().getArgsList().get(Chaincode.ChaincodeInput.ARGS_FIELD_NUMBER).toStringUtf8();
//        String data = chaincodeInvocationSpec.getChaincodeSpec().getInput().getArgsList().get(Chaincode.ChaincodeInput.DECORATIONS_FIELD_NUMBER).toStringUtf8();
//        System.out.println("data__id:" + s);
//        System.out.println("data__::" + data);
//        return data;
//    }
//
//    public List<String> getcurrTxid(){
//        FabricBlock fabricBlock = new FabricBlock(channelName);
//        //获取最新高度的区块信息
//        BlockInfo block = fabricBlock.getBlockByNumber(fabricBlock.getBlockHeight()-1);
//        int envelopeCount = block.getEnvelopeCount();
//        List<String> txIds = new ArrayList<>();
//        for (int i = 0; i < envelopeCount; i++) {
//            BlockInfo.EnvelopeInfo envelopeInfo = null;
//            try {
//                //读取信封信息包含Txid
//                envelopeInfo = block.getEnvelopeInfo(i);
//            } catch (InvalidProtocolBufferException e) {
//                e.printStackTrace();
//            }
//            txIds.add(envelopeInfo.getTransactionID());
//        }
//        return txIds;
//    }
//}
