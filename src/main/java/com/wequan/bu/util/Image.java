package com.wequan.bu.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Zhen Lin
 */

@Component
public class Image {

    public static BufferedImage getImage(String path) {
        File file = new File(path);

        if (!file.exists()) {
            return null;
        }


        BufferedImage res = getImage(file);
        return res;
    }

    public static BufferedImage getImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<BufferedImage> getImages(List<String> paths) {
        if (paths == null || paths.size() <= 0)
            return null;

        int count = paths.size();
        List<BufferedImage> res = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            res.add(getImage(paths.get(i)));
        }

        return res;
    }

    public static BufferedImage[] getImages(File[] files) {
        return null;
    }

    public static void imageToFile(String path, BufferedImage image) {
        File dstFile = new File(path);
        try {
            ImageIO.write(image, "jpeg", dstFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage mergeImages(List<BufferedImage> images) {
        int count = images.size();
        int[][] ImageArrays = new int[count][];
        int dstWidth = 0, dstHeight = 0;
        for (int i = 0; i < count; i++) {
            // get width and height
            BufferedImage image = images.get(i);
            int width = image.getWidth();
            int height = image.getHeight();

            // update width and height of dst image
            dstWidth = Math.max(dstWidth, width);
            dstHeight += height;

            // read RGB pixel
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = image.getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }

        if (dstHeight <= 0 || dstWidth <= 0) {
            return null;
        }

        // merge
        try {
            BufferedImage dstBufferedImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_RGB);
            int currHeight = 0;
            for (int i = 0; i < count; i++) {
                BufferedImage image = images.get(i);
                dstBufferedImage.setRGB(0, currHeight, dstWidth, image.getHeight(), ImageArrays[i], 0, dstWidth);
                currHeight += image.getHeight();
            }

            return dstBufferedImage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
