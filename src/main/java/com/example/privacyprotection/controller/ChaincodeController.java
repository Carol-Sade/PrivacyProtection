package com.example.privacyprotection.controller;

import com.example.privacyprotection.service.ChaincodeService;
import com.example.privacyprotection.utils.FabricUtils;
import com.example.privacyprotection.utils.UploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;


@RestController
@RequestMapping("api/chaincode")
public class ChaincodeController {

    @Autowired
    private ChaincodeService chaincodeService;

    @Autowired
    private UploadFile uploadFile;

    @Autowired
    private FabricUtils fabricUtils;

    @PostMapping("uploadChaincode")
    private Map<String, Object> uploadChaincode(@RequestParam("file") MultipartFile multipartFile,
                                                @RequestParam String chaincodeName) {
        Map<String, Object> map = new HashMap<>();
        String chaincodeUrl = uploadFile.uploadChaincode(multipartFile, chaincodeName);
        Integer code = chaincodeService.createChaincode(chaincodeName, chaincodeUrl);
        if (chaincodeUrl != null && code == 1) {
            map.put("code", 1);
            map.put("msg", "success");
        } else {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("downloadChaincode")
    private Map<String, Object> downloadChaincode(@RequestParam Integer chaincodeId) {
        Map<String, Object> map = new HashMap<>();
        try {
            String chaincodeUrl = chaincodeService.downloadChaincode(chaincodeId);
            if (chaincodeUrl != null) {
                map.put("code", 1);
                map.put("msg", "success");
                map.put("url", chaincodeUrl);
            } else {
                map.put("code", 0);
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("deleteChaincode")
    private Map<String, Object> deleteChaincode(@RequestParam Integer chaincodeId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Integer code = chaincodeService.deleteChaincode(chaincodeId);
            if (code == 1) {
                map.put("code", 1);
                map.put("msg", "success");
            } else {
                map.put("code", 0);
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("getChaincode")
    private Map<String, Object> getChaincode() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("code", 1);
            map.put("msg", "success");
            map.put("list", chaincodeService.getChaincode());
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("installChaincode")
    private Map<String, Object> installChaincode(@RequestParam String chaincodeName,
                                                 @RequestParam String chaincodeVersion) {
        Map<String, Object> map = new HashMap<>();
        try {
            fabricUtils.installChaincode(chaincodeName, chaincodeVersion);
            chaincodeService.installChaincode(chaincodeName,chaincodeVersion);
            map.put("code", 1);
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("instantiateChaincode")
    private Map<String, Object> instantiateChaincode(@RequestParam String chaincodeName,
                                                     @RequestParam String chaincodeVersion) {
        Map<String, Object> map = new HashMap<>();
        try {
            fabricUtils.instantiateChaincode(chaincodeName, chaincodeVersion);
            chaincodeService.instantiateChaincode(chaincodeName,chaincodeVersion);
            map.put("code", 1);
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("upgradeChaincode")
    private Map<String, Object> upgradeChaincode(@RequestParam String chaincodeName,
                                                 @RequestParam String chaincodeVersion) {
        Map<String, Object> map = new HashMap<>();
        try {
            fabricUtils.upgradeChaincode(chaincodeName, chaincodeVersion);
            map.put("code", 1);
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        return map;
    }
}
