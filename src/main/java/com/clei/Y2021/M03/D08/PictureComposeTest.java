package com.clei.Y2021.M03.D08;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片合成
 *
 * @author KIyA
 */
public class PictureComposeTest {

    public static void main(String[] args) throws IOException {

        BufferedImage image = new BufferedImage(480, 360, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = image.createGraphics();

        File bgFile = new File("D:\\Picture\\lp.jpg");
        Image bg = ImageIO.read(bgFile);

        File decorationFile1 = new File("D:\\Work\\KIyA\\temp\\images\\bd.png");
        Image decoration1 = ImageIO.read(decorationFile1);

        File decorationFile2 = new File("D:\\Work\\KIyA\\temp\\images\\bd2.png");
        Image decoration2 = ImageIO.read(decorationFile2);

        // 图片拼接
        graphics.drawImage(bg, 0, 0, null);
        graphics.drawImage(decoration1, 0, 0, null);
        graphics.drawImage(decoration2, 150, 150, null);
        // 添加文字
        graphics.setColor(new Color(0, 128, 255, 128));
        graphics.setFont(new Font("黑体", Font.BOLD, 30));
        graphics.drawString("人生自古谁无死", 50, 50);
        // 输出
        graphics.dispose();
        ImageIO.write(image, "JPEG", new File("D:\\Work\\KIyA\\temp\\images\\result.jpg"));
    }
}
