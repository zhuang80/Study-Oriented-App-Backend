package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.MaterialForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * @author Zhaochao Huang
 */
@RestController
@Api(value="Operations for material", tags="material Rest APi")
public class MaterialController {
    private static final Logger log = LoggerFactory.getLogger(MaterialController.class);

    @PostMapping("/material")
    @ApiOperation(value="", notes="upload course material")
    public String uploadFile(@RequestParam MultipartFile[] files, HttpSession session) throws IOException {
        for(MultipartFile file : files) {
            if(file.getSize() > 0){
                //assign a new unique name for the uploaded file
                System.out.println(file.getContentType());
                String filename = file.getOriginalFilename();
                String[] temps = filename.split("\\.", 2);
                UUID randomUUID = UUID.randomUUID();
                String newFilename = temps[0] + "-" + randomUUID + "." + temps[1];
                //hardcode the file path
                String path = "C:\\Users\\huang\\Desktop\\Wequan\\LocalStorage\\" + newFilename;
                File newFile = new File(path);
                file.transferTo(newFile);
                if(file.getContentType().equals("application/pdf")){
                    PDDocument pd = PDDocument.load(newFile);
                    PDFRenderer pr = new PDFRenderer(pd);
                    for(int page = 0; page < pd.getNumberOfPages(); ++page){
                        if(page > 2) break;
                        BufferedImage bi = pr.renderImageWithDPI(page, 72, ImageType.RGB);
                        String newImage = "C:\\Users\\huang\\Desktop\\Wequan\\LocalStorage\\"+
                                temps[0] + "-" + randomUUID + "-page-" + (page+1)+".jpg";
                        ImageIO.write(bi, "JPEG", new File(newImage));
                    }


                }
            }
        }
        return "success";
    }
}
