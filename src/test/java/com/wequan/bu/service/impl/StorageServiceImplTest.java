package com.wequan.bu.service.impl;

import com.wequan.bu.service.StorageService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceImplTest {

    @Autowired
    StorageService service;

    @Test
    public void upload() {
//        service.upload();
    }

    @Test
    public void download() {
        byte[] bytes = service.download("cv.pdf");
        assert bytes != null;
        System.out.println(bytes.length);
        String path = "./src/test/resources/out/" + UUID.randomUUID().toString() + ".pdf";
        try (FileOutputStream stream = new FileOutputStream(path)) {
            stream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}