package com.wequan.bu.service.impl;

import com.wequan.bu.service.MaterialService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Zhaochao Huang
 */
@Service
public class MaterialServiceImpl implements MaterialService {

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
            if (file.getSize() > 0) {
                //to get the original name of the file
                String filename = file.getOriginalFilename();
                //separate the file name and extension
                String[] temps = filename.split("\\.", 2);
                //generate a UUID to improve the uniqueness of filename
                UUID randomUUID = UUID.randomUUID();
                //append all parts to form a unique file name
                String newFilename = temps[0] + "-" + randomUUID + "." + temps[1];
                //specify the path where the file sits
                String path = basePath + newFilename;
                File newFile = new File(path);
                try{
                    file.transferTo(newFile);
                    ret.add(newFile);
                }catch(Exception e){
                    System.out.println("Fail to store file in local storage.");
                }
            }
        }
        return ret;
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
}
