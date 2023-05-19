package com.example.privacyprotection.utils.fabricSDK;



import com.example.privacyprotection.entity.File;
import com.google.gson.Gson;
import org.junit.Test;


import java.sql.Timestamp;
import java.util.Map;

public class main {


    public static void main(String[] args) throws Exception {
        FabricSDK fabricSDK = new FabricSDK("file", "mychannel");


        File file = new File();

        file.setId(35);
        file.setFileName("pet.sql");
        file.setUserId(1);
        file.setFileDescribe("数据库导出文件");
        file.setFileState(0);
        file.setFileHash("ff0f5741db803857a5d667d3b16f7d2059ec9edfa356e5ed81203003e49eb03b");
        file.setCreateTime(Timestamp.valueOf("2023-05-15 00:28:48"));


        String[] initArgsInvoke =
                {file.getId().toString(),
                        "{\"fileId\":\"" + file.getId() + "\"," +
                                "\"fileName\":\"" + file.getFileName() + "\"," +
                                "\"userId\":\"" + file.getUserId() + "\"," +
                                "\"fileDescribe\":\"" + file.getFileDescribe() + "\"," +
                                "\"fileState\":\"" + file.getFileState() + "\"," +
                                "\"fileHash\":\"" + file.getFileHash() + "\"," +
                                "\"CreateTime\":\"" + file.getCreateTime() + "\"}"
                };
        boolean code = fabricSDK.invoke(initArgsInvoke);
        System.out.println(code);


//        Object object = fabricSDK.installChaincode("2.0", "/Users/parsifal/Downloads/PrivacyProtection/chaincode", "file");
//        System.out.println(object);

//        Object object = fabricSDK.upgradeChaincode("file", "2.0");
//        System.out.println(object);

//        Object object = fabricSDK.instantiated("file", "1.0");
//        System.out.println(object);


//        String[] initArgsQuery = {"35"};
//        Map result = fabricSDK.queryChaincode(initArgsQuery);
//        Gson gson = new Gson();
//        Map map = gson.fromJson(result.get("data").toString(), Map.class);
//        System.out.println(map);
    }



}
