package com.wequan.bu.service;

import com.wequan.bu.controller.vo.MultipartFileWrapper;
import com.wequan.bu.repository.model.Material;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface MaterialService extends Service<Material> {

    /**
     * store the files send from the client on local storage
     * @param files the files send from the client
     * @param basePath the directory path where files sit
     * @return the list of files of type File
     */
    public List<File> uploadFiles(MultipartFile[] files, String basePath) throws IOException;

    public File uploadFile(MultipartFile file, String basePath) throws IOException;

    /**
     *
     * @param files a list of files of pdf MIME type
     * @param basePath the directory path where files sit
     */
    public void convertPdfToImage(List<File> files, String basePath) throws IOException;

    /**
     * find a list of materials according to combination of course id and professor id
     * @param c_id course id
     * @param p_id professor id
     * @param pageNum page number
     * @param pageSize the size of each page
     * @return a list of material uploaded for a certain course taught by a certain professor
     */
    public List<Material> findByCourseIdAndProfessorId(Integer c_id, Integer p_id, Integer pageNum, Integer pageSize);

    public void uploadSupportMaterial(MultipartFileWrapper files, String basePath) throws IOException;

}
