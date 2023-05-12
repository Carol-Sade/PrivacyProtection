package com.example.privacyprotection.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class PictureCompression {

    @Value("${upload.location}")
    private String location;

    private static final Integer ZERO = 0;
    private static final Integer ONE_ZERO_TWO_FOUR = 1024;
    private static final Integer NINE_ZERO_ZERO = 900;
    private static final Integer THREE_TWO_SEVEN_FIVE = 3275;
    private static final Integer TWO_ZERO_FOUR_SEVEN = 2047;
    private static final Double ZERO_EIGHT_FIVE = 0.85;
    private static final Double ZERO_SIX = 0.6;
    private static final Double ZERO_FOUR_FOUR = 0.44;
    private static final Double ZERO_FOUR = 0.4;

    public Integer compress() {
        File dir = new File(location);
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            String type = file.getName().substring(file.getName().lastIndexOf("."));
            if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg") || type.equals(".gif")) {
                try {
                    InputStream inputStream = new FileInputStream(file);
                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
                    byte[] bytes = compressPicForScale(multipartFile.getBytes(), 500);
                    File fOut = new File(file.getPath());
                    FileOutputStream fileOutputStream = new FileOutputStream(fOut);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (Exception e) {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        if (imageBytes == null || imageBytes.length <= ZERO || imageBytes.length < desFileSize * ONE_ZERO_TWO_FOUR) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / ONE_ZERO_TWO_FOUR);
        try {
            while (imageBytes.length > desFileSize * ONE_ZERO_TWO_FOUR) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            //System.out.println("图片原大小=" + srcSize / ONE_ZERO_TWO_FOUR + " 压缩后大小=" + imageBytes.length / ONE_ZERO_TWO_FOUR);
        } catch (Exception e) {
            //System.out.println("error");
            return null;
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private double getAccuracy(long size) {
        double accuracy;
        if (size < NINE_ZERO_ZERO) {
            accuracy = ZERO_EIGHT_FIVE;
        } else if (size < TWO_ZERO_FOUR_SEVEN) {
            accuracy = ZERO_SIX;
        } else if (size < THREE_TWO_SEVEN_FIVE) {
            accuracy = ZERO_FOUR_FOUR;
        } else {
            accuracy = ZERO_FOUR;
        }
        return accuracy;
    }
}
