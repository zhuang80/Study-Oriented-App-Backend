package com.wequan.bu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface MaterialService {

    /**
     * store the files send from the client on local storage
     * @param files the files send from the client
     * @param basePath the directory path where files sit
     * @return the list of files of type File
     */
    public List<File> uploadFiles(MultipartFile[] files, String basePath) throws IOException;

    /**
     *
     * @param files a list of files of pdf MIME type
     * @param basePath the directory path where files sit
     */
    public void convertPdfToImage(List<File> files, String basePath) throws IOException;
}
