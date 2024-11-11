package view.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageComparison {
    public static boolean areIconsEqual(ImageIcon icon1, ImageIcon icon2) {
        if (icon1 == null || icon2 == null) {
            return false;
        }

        Image img1 = icon1.getImage();
        Image img2 = icon2.getImage();

        BufferedImage bufferedImg1 = toBufferedImage(img1);
        BufferedImage bufferedImg2 = toBufferedImage(img2);

        if (bufferedImg1.getWidth() != bufferedImg2.getWidth() || bufferedImg1.getHeight() != bufferedImg2.getHeight()) {
            return false;
        }

        for (int x = 0; x < bufferedImg1.getWidth(); x++) {
            for (int y = 0; y < bufferedImg1.getHeight(); y++) {
                if (bufferedImg1.getRGB(x, y) != bufferedImg2.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bImg.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bImg;
    }
}