package com.example.privacyprotection.utils;

import com.example.privacyprotection.entity.File;
import com.example.privacyprotection.utils.fabricSDK.FabricSDK;
import org.springframework.stereotype.Component;

@Component
public class FabricUtils {

    public Integer insert(File file) {
        try {
            FabricSDK fabricSDK = new FabricSDK("file", "mychannel");
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
            if (code) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
