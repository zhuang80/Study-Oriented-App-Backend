package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.MultipartFileWrapper;
import com.wequan.bu.repository.dao.MaterialMapper;
import com.wequan.bu.repository.dao.TutorApplicationSupportMaterialMapper;
import com.wequan.bu.repository.model.Material;
import com.wequan.bu.repository.model.TutorApplicationSupportMaterial;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.MaterialService;
import com.wequan.bu.service.StorageService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Zhaochao Huang
 */
@Service
public class MaterialServiceImpl extends AbstractService<Material> implements MaterialService {
    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private TutorApplicationSupportMaterialMapper supportMaterialMapper;

    @Autowired
    private StorageService storageService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(materialMapper);
    }


    /**
     * store the files send from the client on local storage
     * @param files the files send from the client
     * @param basePath the directory path where files sit
     * @return a list of files of type File
     * @throws IOException
     */
    @Override
    public List<File> uploadFiles(MultipartFile[] files, String basePath) throws IOException {
        List<File> ret = new ArrayList<>();
        for(MultipartFile file : files) {
           ret.add(uploadFile(file, basePath));
        }
        return ret;
    }

    @Override
    public File uploadFile(MultipartFile multipartFile, String basePath) throws IOException {
        File file = null;
        if (multipartFile.getSize() > 0) {
            //to get the original name of the file
            String filename = multipartFile.getOriginalFilename();
            //to generate a unique filename based on original name
            String newFilename = getUniqueFileName(filename);
            //specify the path where the file sits
            String path = basePath + newFilename;
            file = new File(path);
            try{
                multipartFile.transferTo(file);
            }catch(Exception e){
                System.out.println("Fail to store file in local storage.");
            }
        }
        return file;
    }

    /**
     * make the file name more unique among S3 bucket
     * @param filename the original file name
     * @return the new file name
     */
    public String getUniqueFileName(String filename){
        //separate the file name and extension
        String[] temps = filename.split("\\.", 2);
        //generate a UUID to improve the uniqueness of filename
        UUID randomUUID = UUID.randomUUID();
        //append all parts to form a unique file name
        String newFilename = temps[0] + "-" + randomUUID + "." + temps[1];
        return newFilename;
    }

    /**
     * convert PDF file to Image, it is an async method
     * @param files a list of files of pdf MIME type
     */
    @Async
    @Override
    public void convertPdfToImage(List<File> files, String basePath) throws IOException {
        Long begin, end;
        for(File file : files) {
            String type = Files.probeContentType(file.toPath());
            if (type.equals("application/pdf")) {
                begin = System.currentTimeMillis();
                PDDocument pd = PDDocument.load(file);
                PDFRenderer pr = new PDFRenderer(pd);
                end = System.currentTimeMillis();
                System.out.println("Load time : " + (end - begin));
                for(int page = 0; page < pd.getNumberOfPages(); ++page) {
                    begin = System.currentTimeMillis();
                    BufferedImage bi = pr.renderImageWithDPI(page, 72, ImageType.RGB);

                    String[] temps = file.getName().split("\\.", 2);
                    String newImage = basePath + temps[0] + "-page-" + (page + 1) + ".jpg";
                    ImageIO.write(bi, "JPEG", new File(newImage));
                    end = System.currentTimeMillis();
                    System.out.println("Time cost : " + (end - begin));
                }
            }
        }
    }

    @Override
    public List<Material> findByCourseIdAndProfessorId(Integer c_id, Integer p_id, Integer pageNum, Integer pageSize) {
        return materialMapper.selectByCourseIdAndProfessorId(c_id, p_id);
    }

    @Override
    public List<Integer> uploadSupportMaterial(MultipartFileWrapper filesWrapper, String basePath) throws IOException {
        List<Integer> idList = new ArrayList<>();
        String path = "application/";
        String key;
        File tempFile = null;
        for(MultipartFile multipartFile : filesWrapper.getFiles()){
            try{
                TutorApplicationSupportMaterial material = new TutorApplicationSupportMaterial();
                material.setFileName(multipartFile.getOriginalFilename());

                //first transfer multipart file to local file
                tempFile = uploadFile(multipartFile, basePath);
                key = path + tempFile.getName();
                //try to upload file to S3 bucket
                if(storageService.uploadFile(key, tempFile)){
                    System.out.println("-------------------------"+ tempFile.getName().length());
                    material.setType(filesWrapper.getType());
                    material.setStorePath(key);
                    material.setFileType(Files.probeContentType(tempFile.toPath()));
                    material.setUuid(tempFile.getName());
                    material.setUploadBy(filesWrapper.getUploadBy());
                    material.setUploadTime(LocalDateTime.now());
                    material.setSourceFrom((short) 4);
                    System.out.println("============================================= before id: " + material.getId());
                    supportMaterialMapper.insertSelective(material);
                    System.out.println("============================================= id: " + material.getId());
                    idList.add(material.getId());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                if(tempFile != null){
                    if(tempFile.delete()){
                        System.out.println(tempFile.getName() + " deleted successfully!");
                    }else{
                        System.out.println("fail to delete file" + tempFile.getName());
                    }
                }
            }
        }
        return idList;
    }
}
