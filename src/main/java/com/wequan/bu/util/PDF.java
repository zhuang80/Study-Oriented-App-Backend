package com.wequan.bu.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDF {
    public static List<BufferedImage> PDFBytesToImages(byte[] bytes) {
        List<BufferedImage> res = new ArrayList<>();
        try {
            PDDocument pd = PDDocument.load(bytes);
            PDFRenderer pr = new PDFRenderer(pd);
            for (int page = 0; page < pd.getNumberOfPages(); ++page) {
                BufferedImage image = pr.renderImageWithDPI(page, 72, ImageType.RGB);
                res.add(image);
            }
            pd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public static BufferedImage PDFBytesToImage(byte[] bytes) {
        return Image.mergeImages(PDFBytesToImages(bytes));
    }
}
