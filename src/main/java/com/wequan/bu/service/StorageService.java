package com.wequan.bu.service;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Zhen Lin
 */
public interface StorageService {
    //    public boolean upload(String key, File file);

    boolean uploadBufferedImage(String key, BufferedImage image);

    /**
     * Download
     */
    byte[] download(String key);

    /**
     * getPresignedURL
     * @param key filename
     * @return url
     */
    URL getPresignedURL(String key);
}