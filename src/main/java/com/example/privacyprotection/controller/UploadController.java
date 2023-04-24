package com.example.privacyprotection.controller;

import com.jcraft.jsch.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import java.util.Properties;


@RestController
@RequestMapping("api/upload")
public class UploadController {

    @PostMapping("test")
    public Map<String, Object> test(@RequestParam MultipartFile multipartFile) throws JSchException, FileNotFoundException, SftpException {
        Map<String, Object> map = new HashMap<>();

        String host = "8.130.79.250"; // SFTP 主机名
        int port = 22; // SFTP 端口号
        String username = "root"; // SFTP 用户名
        String password = "Sj2000617"; // SFTP 密码
        String remoteDir = "/opt/"; // SFTP 远程目录
        String localFile = "example.txt"; // 本地文件路径

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        // 设置连接参数
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        // 连接 SFTP 服务器
        session.connect();

        // 打开 SFTP 通道
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();

        // 上传文件
        String fileName = multipartFile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        try {
            // 将 MultipartFile 转换为 File
            File convertedFile = File.createTempFile(fileName, prefix);
            multipartFile.transferTo(convertedFile);
            sftp.put(new FileInputStream(convertedFile), remoteDir + fileName);
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
        // 关闭 SFTP 通道和会话
        sftp.disconnect();
        session.disconnect();
        return map;
    }
}
