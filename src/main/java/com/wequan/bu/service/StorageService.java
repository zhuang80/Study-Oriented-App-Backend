package com.wequan.bu.service;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public interface StorageService {
    //    public boolean upload(String key, File file);
    public boolean uploadBufferedImage(String key, BufferedImage image);

    public byte[] download(String key);
}