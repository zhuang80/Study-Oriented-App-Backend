package com.wequan.bu.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Zhen Lin
 */
public class ImageTest {

    private String kartikImagePath = "./src/test/resources/kartik.jpg";
    private String outputPath = "./src/test/resources/out/";


    @Before
    private void createOutDir() {
        File file = new File(outputPath);
        file.mkdirs();
    }

    @Test
    public void testGetImageFromPath() {
        BufferedImage image = Image.getImage(kartikImagePath);
        testImage(image);
    }

    @Test
    public void testGetImageFromFile() {
        BufferedImage image = Image.getImage(new File(kartikImagePath));
        testImage(image);
    }

    @Test
    public void testGetImagesFromPaths() {
        List<String> paths = new ArrayList(Collections.nCopies(3, kartikImagePath));
        List<BufferedImage> images = Image.getImages(paths);
        for (BufferedImage image : images) {
            testImage(image);
        }
    }

    @Test
    public void testGetImagesFromFiles() {
    }

    @Test
    public void mergeImages() {
        List<String> paths = new ArrayList(Collections.nCopies(3, kartikImagePath));
        List<BufferedImage> images = Image.getImages(paths);
        String filename = UUID.randomUUID().toString() + ".jpg";
        BufferedImage image = Image.mergeImages(images);
        assert image != null && image.getWidth() == 480 && image.getHeight() == 1800;
        Image.imageToFile(outputPath + filename, image);
    }

    private void testImage(BufferedImage image) {
        assert image != null && image.getWidth() == 480 && image.getHeight() == 600;
    }
}