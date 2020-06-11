package com.wequan.bu.service.impl;

import com.wequan.bu.service.StorageService;
import com.wequan.bu.util.FileTool;
import com.wequan.bu.util.Image;
import com.wequan.bu.util.PDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceImplTest {

    @Autowired
    StorageService service;

    private String outputPath = "./src/test/resources/out/";

    @Before
    public void createOutDir() {
        File file = new File(outputPath);
        file.mkdirs();
    }

    @Test
    public void upload() {
//        service.upload();
    }

    @Test
    public void download() {
        byte[] bytes = service.download("test_presigned_url.pdf");
        assert bytes != null;
        System.out.println(bytes.length);
        String path = outputPath + UUID.randomUUID().toString() + ".pdf";
        try (FileOutputStream stream = new FileOutputStream(path)) {
            stream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getPresignedURL() {
        URL url = service.getPresignedURL(UUID.randomUUID().toString() + ".pdf");
        String urlStr = url.toString();
        assert urlStr.startsWith("https://") || urlStr.startsWith("http://");
        System.out.println(urlStr);
    }

    @Test
    public void uploadViaPreSignedURL() {
        URL url = service.getPresignedURL("test_presigned_url.pdf");
        try {
            System.out.println("Pre-signed URL to upload a file to: " + url);

            // Create the connection and use it to upload the new object by using the pre-signed URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/pdf");
            connection.setRequestMethod("PUT");
            File file = new File("./src/test/resources/atc17_slides_zhang_hao.pdf");
            BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int i;
            byte[] buffer = new byte[4096];
            while ((i = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, i);
            }
            bis.close();
            bos.close();
            connection.getResponseCode();
            System.out.println(connection.getContent());
            System.out.println("HTTP response code: " + connection.getResponseCode());

            /*
             *  It's recommended that you close the S3Presigner when it is done being used, because some credential
             * providers (e.g. if your AWS profile is configured to assume an STS role) require system resources
             * that need to be freed. If you are using one S3Presigner per application (as recommended), this
             * usually isn't needed
             */
//            presigner.close();

        } catch (S3Exception e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }

        byte[] bytes = service.download("test_presigned_url.pdf");
        BufferedImage image = PDF.PDFBytesToImage(bytes);
        Image.imageToFile(outputPath + "test_presigned_url" + ".jpg", image);

    }
}