package com.wequan.bu.service.impl;

import com.wequan.bu.controller.AppointmentController;
import com.wequan.bu.controller.vo.UploadFileWrapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);
    /**
     * store the files send from the client on local storage
     * @param files the files send from the client
     * @param basePath the directory path where files sit
     * @return a list of files of type File
     * @throws IOException
     */
    @Override
    public List<File> uploadFiles(MultipartFile[] files, String basePath) throws IOException {
        if(files == null) return null;
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

    public String getOriginalName(String filename){
        String[] temps = filename.split("\\.", 2);
        int index = temps[0].indexOf("-");
        return temps[0].substring(0,index) + "." +temps[1];
    }

    public String getFileName(String path) {
        log.info("===========================> path name: "+ path);
        int index = path.lastIndexOf('\\');
        String name = path.substring(index + 1);
        log.info("=========================> file name: " + name);
        return name;
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
    @Transactional(rollbackFor = Exception.class)
    public List<Integer> uploadSupportMaterial(UploadFileWrapper filesWrapper) throws IOException {
        if(filesWrapper == null) return null;
        List<Integer> idList = new ArrayList<>();
        String path = "application/";
        String key;

        for(File file : filesWrapper.getFiles()){
            try{
                TutorApplicationSupportMaterial material = new TutorApplicationSupportMaterial();
                material.setFileName(getOriginalName(file.getName()));

                key = path + getFileName(file.getName());
                //try to upload file to S3 bucket
                log.info("===========================S3 key"+key);
                if(storageService.uploadFile(key, file)){
                    material.setType(filesWrapper.getType());
                    material.setStorePath(key);
                    material.setFileType(Files.probeContentType(file.toPath()));
                    material.setUuid(file.getName());
                    material.setUploadBy(filesWrapper.getUploadBy());
                    material.setUploadTime(LocalDateTime.now());
                    material.setSourceFrom((short) 4);

                    supportMaterialMapper.insertSelective(material);

                    idList.add(material.getId());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                if(file != null){
                    if(file.delete()){
                        log.info(file.getName() + " deleted successfully!");
                    }else{
                        log.info("fail to delete file" + file.getName());
                    }
                }
            }
        }
        return idList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSupportMaterialById(Integer id){
        //get the file meta data
        TutorApplicationSupportMaterial supportMaterial = supportMaterialMapper.selectByPrimaryKey(id);
        if(supportMaterial != null) {
            //delete file from S3
            storageService.deleteObject(supportMaterial.getStorePath());
            //delete file meta data from table
            supportMaterialMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSupportMaterialsByIds(String ids) {
        //get the file meta data
        if(ids != null && !ids.isEmpty()) {
            List<TutorApplicationSupportMaterial> supportMaterials = supportMaterialMapper.selectByIds(ids);
            for(TutorApplicationSupportMaterial supportMaterial : supportMaterials){
                //delete file from S3
                storageService.deleteObject(supportMaterial.getStorePath());
            }
            supportMaterialMapper.deleteByIds(ids);
        }
    }
}
