package com.example.privacyprotection.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class MD5 {
    public String MD5Encode(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md5 != null;
        return Base64.getEncoder().encodeToString(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }
}
