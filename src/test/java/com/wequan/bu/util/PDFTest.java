package com.wequan.bu.util;

import com.wequan.bu.service.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PDFTest {

    private String outputPath = "./src/test/resources/out/";

    @Before
    public void createOutDir() {
        File file = new File(outputPath);
        file.mkdirs();
    }

    @Autowired
    private StorageService storage;

    @Test
    public void PDFBytesToImage() {
//        byte[] bytes = storage.download("cv.pdf");
        byte[] bytes = storage.download("atc17_slides_zhang_hao.pdf");
//        byte[] bytes = storage.download("test.pdf");
        BufferedImage image = PDF.PDFBytesToImage(bytes);
        Image.imageToFile(outputPath + UUID.randomUUID().toString() + ".jpg", image);
    }
}