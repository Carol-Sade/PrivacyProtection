package com.example.privacyprotection.utils;

import com.example.privacyprotection.entity.File;
import com.example.privacyprotection.utils.fabricSDK.FabricSDK;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Map;


@Component
public class FabricUtils {

    @Value("${upload.chaincodeLocation}")
    private String chaincodeLocation;

    @Value("${upload.location}")
    private String location;

    public String countHash(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            FileInputStream fileInputStream = new FileInputStream(location + file.getFilePath());
            byte[] buffer = new byte[8192];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fileInputStream.close();
            byte[] hash = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }


    public Integer checkHash(File file) {
        String hash = countHash(file);
        Map map = queryFile(file);
        if (map != null) {
            if (map.get("fileHash").equals(hash)) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public void installChaincode(String chaincodeName, String chaincodeVersion) throws Exception {
        FabricSDK fabricSDK = new FabricSDK(chaincodeName, "mychannel");
        fabricSDK.installChaincode(chaincodeVersion, "/Users/parsifal/Downloads/PrivacyProtection/chaincode/", chaincodeName);
    }

    public void instantiateChaincode(String chaincodeName, String chaincodeVersion) throws Exception {
        FabricSDK fabricSDK = new FabricSDK(chaincodeName, "mychannel");
        fabricSDK.instantiated(chaincodeName, chaincodeVersion);
    }

    public void upgradeChaincode(String chaincodeName, String chaincodeVersion) throws Exception {
        FabricSDK fabricSDK = new FabricSDK(chaincodeName, "mychannel");
        fabricSDK.upgradeChaincode(chaincodeName, chaincodeVersion);
    }

    public Integer insertFile(File file) {
        try {
            FabricSDK fabricSDK = new FabricSDK("file", "mychannel");
            String[] initArgsInvoke =
                    {file.getId().toString(),
                            "{\"fileId\":\"" + file.getId() + "\"," +
                                    "\"fileName\":\"" + file.getFileName() + "\"," +
                                    "\"userId\":\"" + file.getUserId() + "\"," +
                                    "\"fileDescribe\":\"" + file.getFileDescribe() + "\"," +
                                    "\"fileHash\":\"" + file.getFileHash() + "\"," +
                                    "\"CreateTime\":\"" + file.getCreateTime() + "\"}"
                    };
            boolean code = fabricSDK.invoke(initArgsInvoke);
            if (code) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public Map queryFile(File file) {
        try {
            FabricSDK fabricSDK = new FabricSDK("file", "mychannel");
            String[] initArgsQuery = {file.getId().toString()};
            Map result = fabricSDK.queryChaincode(initArgsQuery);
            Gson gson = new Gson();
            Map map = gson.fromJson(result.get("data").toString(), Map.class);
            return map;
        } catch (Exception e) {
            return null;
        }
    }
}
