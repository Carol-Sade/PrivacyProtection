package com.example.privacyprotection.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UploadFile {

    @Value("${upload.location}")
    private String location;

    @Value("${upload.avatarLocation}")
    private String avatarLocation;

    @Value(("${upload.url}"))
    private String url;

    @Value(("${upload.avatarUrl}"))
    private String avatarUrl;

    @Autowired
    private PictureCompression pictureCompression;

    public String uploadFile(MultipartFile multipartFile) throws Exception {
        String filePath = "";
        String realPath = location;
        File dir = new File(realPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            String kzm = fileName.substring(fileName.lastIndexOf("."));
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            InputStream inputStream = multipartFile.getInputStream();
            byte[] buffer = new byte[8192];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            inputStream.close();
            byte[] hash = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            byte[] bytes;
            if (kzm.equals(".png") || kzm.equals(".jpg") || kzm.equals(".jpeg") || kzm.equals(".gif")) {
                bytes = pictureCompression.compressPicForScale(multipartFile.getBytes(), 500);
            } else {
                bytes = multipartFile.getBytes();
            }
            String filename = hexString + kzm;
            File file1 = new File(realPath + filename);
            OutputStream out = new FileOutputStream(file1);
            out.write(bytes);
            out.close();
            filePath = filename;
        }
        return filePath;
    }

    public String uploadAvatar(MultipartFile multipartFile) throws Exception {
        String filePath = "";
        String realPath = avatarLocation;
        File dir = new File(realPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String kzm = fileName.substring(fileName.lastIndexOf("."));
            if (kzm.equals(".png") || kzm.equals(".jpg") || kzm.equals(".jpeg") || kzm.equals(".gif")) {
                String filename = uuid + kzm;
                File file1 = new File(realPath + filename);
                OutputStream out = new FileOutputStream(file1);
                out.write(multipartFile.getBytes());
                out.close();
                filePath = filename;
            } else {
                filePath = null;
            }
        }
        return avatarUrl + filePath;
    }

    public List<String> getPictures() {
        List<String> list = new ArrayList<>();
        File dir = new File(location);
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            String type = file.getName().substring(file.getName().lastIndexOf("."));
            if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg") || type.equals(".gif")) {
                list.add(file.getName());
            }
        }
        return list;
    }

    public Integer deletePicture(String filename) {
        File file = new File(location + filename);
        try {
            file.delete();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public Double getPicturesSize() {
        File dir = new File(location);
        File[] files = dir.listFiles();
        double size = 0;
        assert files != null;
        for (File file : files) {
            String type = file.getName().substring(file.getName().lastIndexOf("."));
            if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg") || type.equals(".gif")) {
                size += file.length();
            }
        }
        return size / (1024 * 1024);
    }
}
