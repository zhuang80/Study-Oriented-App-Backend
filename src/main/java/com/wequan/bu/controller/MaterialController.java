package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.MaterialForm;
import com.wequan.bu.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;


/**
 * @author Zhaochao Huang
 */
@RestController
@Api(value="Operations for material", tags="material Rest APi")
@EnableAsync
public class MaterialController {
    private static final Logger log = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private MaterialService materialService;

    @PostMapping("/material")
    @ApiOperation(value="", notes="upload course material")
    public String uploadFile(@RequestParam MultipartFile[] files, HttpSession session) throws IOException {
        Long begin, end;
        String basePath = "C:\\Users\\huang\\Desktop\\Wequan\\LocalStorage\\";
        List<File> returnedFile = materialService.uploadFiles(files, basePath);
        materialService.convertPdfToImage(returnedFile, basePath);
        System.out.println("================================> Send response back to client. ");
        return "success";
    }
}
