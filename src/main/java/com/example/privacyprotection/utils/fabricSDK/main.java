package com.example.privacyprotection.utils.fabricSDK;


import com.example.privacyprotection.entity.File;
import com.example.privacyprotection.entity.User;
import com.google.gson.Gson;
import org.junit.Test;


import java.sql.Timestamp;
import java.util.Map;

public class main {

    public static void install() throws Exception {
        FabricSDK fabricSDK = new FabricSDK("user", "mychannel");
        Object object = fabricSDK.installChaincode("3.0", "/Users/parsifal/Downloads/PrivacyProtection/chaincode", "user");
        System.out.println(object);
    }

    public static void instantiated() throws Exception {
        FabricSDK fabricSDK = new FabricSDK("user", "mychannel");
        Object object = fabricSDK.instantiated("user", "2.0");
        System.out.println(object);
    }

    public static void upgrade() throws Exception {
        FabricSDK fabricSDK = new FabricSDK("user", "mychannel");
        Object object = fabricSDK.upgradeChaincode("user", "3.0");
        System.out.println(object);
    }

    public static void invokeFile() {
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
    }

    public static void invokeUser() {
        FabricSDK fabricSDK = new FabricSDK("user", "mychannel");
        User user = new User();

        user.setId(18);
        user.setUsername("Jason");
        user.setRole(2);
        user.setCreate_time(new Timestamp(System.currentTimeMillis()));

        String[] initArgsInvoke =
                {user.getId().toString(),
                        "{\"userId\":\"" + user.getId() + "\"," +
                                "\"name\":\"" + "admin" + "\"," +
                                "\"roles\":\"" + user.getRole() + "\"," +
                                "\"account\":\"" + user.getUsername() + "\"," +
                                "\"affiliation\":\"" + "Org1" + "\"," +
                                "\"mspId\":\"" + "Org1MSP" + "\"," +
                                "\"CreateTime\":\"" + user.getCreate_time() + "\"}"
                };
        boolean code = fabricSDK.invoke(initArgsInvoke);
        System.out.println(code);
    }

    public static void query() {
        FabricSDK fabricSDK = new FabricSDK("user", "mychannel");
        String[] initArgsQuery = {"18"};
        Map result = fabricSDK.queryChaincode(initArgsQuery);
        Gson gson = new Gson();
        Map map = gson.fromJson(result.get("data").toString(), Map.class);
        System.out.println(map);
    }

    public static void main(String[] args) throws Exception {
        invokeUser();
    }


}
