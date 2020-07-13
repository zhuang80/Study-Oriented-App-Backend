package com.wequan.bu.service;


import org.springframework.http.HttpMethod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * @author Zhen Lin
 */
public interface StorageService {

    boolean uploadBufferedImage(String key, BufferedImage image);

    /**
     * 上传文件
     * @param key key
     * @param file 文件
     * @return 上传成功与否
     */
    boolean uploadFile(String key, File file);

    /**
     * 根据key下载文件
     * @param key key
     * @return byte[]
     */
    byte[] download(String key);

    /**
     * 根据key获取预签名url
     * @param key filename
     * @return url
     */
    URL getPresignedURL(String key, HttpMethod httpMethod);

    /**
     * 根据key删除文件
     * @param key key
     */
    void deleteObject(String key);
}