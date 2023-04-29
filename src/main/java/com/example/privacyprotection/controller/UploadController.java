package com.example.privacyprotection.controller;

import com.jcraft.jsch.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;


import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("api/upload")
public class UploadController {

    String host = "8.130.79.250"; // SFTP 主机名
    int port = 22; // SFTP 端口号
    String username = "root"; // SFTP 用户名
    String password = "Sj2000617"; // SFTP 密码

    @Value("${upload.location}")
    private String location;

    @Value("${upload.url}")
    private String url;

    @PostMapping("upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam String path) throws JSchException {
        Map<String, Object> map = new HashMap<>();

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
            sftp.put(new FileInputStream(convertedFile), path + "/" + fileName);
            map.put("code", 1);
            map.put("msg", "success");
        } catch (Exception e) {
            // 处理异常
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        // 关闭 SFTP 通道和会话
        sftp.disconnect();
        session.disconnect();
        return map;
    }

    @PostMapping("download")
    public Map<String, Object> download(@RequestParam String path) throws JSchException {
        Map<String, Object> map = new HashMap<>();

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");

        // 连接远程服务器
        session.connect();

        // 创建 SFTP 通道
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        try {
            // 下载文件
            sftp.get(path, location);
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            // 关闭 SFTP 通道和会话
            sftp.exit();
            session.disconnect();
            map.put("code", 1);
            map.put("msg", "success");
            map.put("file", url + fileName);
        } catch (Exception e) {
            // 处理异常
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("delete")
    public Map<String, Object> delete(@RequestParam String path) throws JSchException {
        Map<String, Object> map = new HashMap<>();

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

        // 将 MultipartFile 转换为 File
        try {
            sftp.rm(path);
            map.put("code", 1);
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        // 关闭 SFTP 通道和会话
        sftp.disconnect();
        session.disconnect();
        return map;
    }

    @PostMapping("getPath")
    public Map<String, Object> getPath(@RequestParam String path) throws JSchException {
        Map<String, Object> map = new HashMap<>();

        Session session;
        ChannelSftp sftp;

        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);

        // 使用密码登录
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();

        try {
            Vector<ChannelSftp.LsEntry> files = sftp.ls(path);
            List<Map<String, Object>> fileItems = new ArrayList<>();
            List<Map<String, Object>> dirItems = new ArrayList<>();
            for (ChannelSftp.LsEntry entry : files) {
                /* 如果是文件 */
                if (!entry.getAttrs().isDir()) {
                    /* 获取文件名和时间戳 */
                    String fileName = entry.getFilename();
                    long lTimestamp = entry.getAttrs().getMTime() * 1000L;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    /* 将时间戳转换成字符串格式 */
                    Date date = new Date(lTimestamp);
                    String sTimestamp = sdf.format(date);

                    /* 将文件名和时间添加到 map 中 */
                    Map<String, Object> fileInfo = new HashMap<>();
                    fileInfo.put("fileName", fileName);
                    fileInfo.put("timeStamp", sTimestamp);
                    fileInfo.put("type", "file");
                    fileItems.add(fileInfo);
                } else {
                    String fileName = entry.getFilename();
                    if (Objects.equals(entry.getFilename(), ".") || Objects.equals(entry.getFilename(), "..")) {
                        continue;
                    }
                    long lTimestamp = entry.getAttrs().getMTime() * 1000L;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    /* 将时间戳转换成字符串格式 */
                    Date date = new Date(lTimestamp);
                    String sTimestamp = sdf.format(date);

                    /* 将文件名和时间添加到 map 中 */
                    Map<String, Object> fileInfo = new HashMap<>();
                    fileInfo.put("fileName", fileName);
                    fileInfo.put("timeStamp", sTimestamp);
                    fileInfo.put("type", "directory");
                    dirItems.add(fileInfo);
                }
            }
            Comparator<Map<String, Object>> comparator = (p1, p2) -> {
                String time1 = (String) p1.get("timeStamp");
                String time2 = (String) p2.get("timeStamp");
                return time2.compareTo(time1);
            };
            dirItems.sort(comparator);
            fileItems.sort(comparator);
            dirItems.addAll(fileItems);
            map.put("code", 1);
            map.put("msg", "success");
            map.put("list", dirItems);
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        } finally {
            sftp.disconnect();
            session.disconnect();
        }
        return map;
    }

    @PostMapping("getAll")
    public Map<String, Object> getAll(@RequestParam String path) {
        Map<String, Object> map = new HashMap<>();

        Session session = null;
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);

            // 使用密码登录
            session.setPassword(password);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            List<String> result = new ArrayList<>();
            recurseListFiles(sftp, path, result);
            map.put("list", result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return map;
    }

    private void recurseListFiles(ChannelSftp sftp, String remotePath, List<String> result) throws Exception {
        Vector<ChannelSftp.LsEntry> files = sftp.ls(remotePath);
        for (ChannelSftp.LsEntry file : files) {
            String fileName = file.getFilename();
            if (".".equals(fileName) || "..".equals(fileName)) {
                continue;
            }
            String filePath = remotePath + "/" + fileName;
            System.out.println(filePath);
            if (file.getAttrs().isDir()) {
                // 如果是目录，则递归读取目录下的文件列表
                recurseListFiles(sftp, filePath, result);
            } else {
                // 如果是文件，则加入结果集
                result.add(filePath);
            }
        }
    }
}
