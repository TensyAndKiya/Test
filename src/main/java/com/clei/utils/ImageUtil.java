package com.clei.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 图片处理工具类
 *
 * @author KIyA
 */
public class ImageUtil {

    /**
     * 点符号
     */
    private final static String DOT = ".";

    public static void main(String[] args) throws Exception {
        // cut("D:\\Picture\\Mary\\Mary_0.png", "D:\\Picture\\Mary_cut.png", 300, 300, 1256, 685);
        // scale("D:\\Picture\\Mary\\Mary_0.png", "D:\\Picture\\Mary_scale.png", 628, 342);
        // inversion("D:\\Picture\\Mary\\Mary_0.png", "D:\\Picture\\Mary_inversion.png");
        rotate("D:\\Picture\\Mary\\Mary_0.png", "D:\\Picture\\Mary_rotate.png", 300);
    }

    /**
     * 裁剪图片
     *
     * @param imageFilePath  源图片文件路径
     * @param targetFilePath 裁剪图片存放路径，需包含文件名
     * @param x              x起点
     * @param y              y起点
     * @param w              宽度
     * @param h              高度
     * @throws Exception 各种异常
     */
    public static void cut(String imageFilePath, String targetFilePath, int x, int y, int w, int h) throws Exception {
        File imageFile = new File(imageFilePath);
        BufferedImage image = ImageIO.read(imageFile);
        int width = image.getWidth() - x;
        int height = image.getHeight() - y;
        w = Math.min(w, width);
        h = Math.min(h, height);
        BufferedImage resultImage = image.getSubimage(x, y, w, h);
        // 输出
        String suffix = imageFilePath.substring(imageFilePath.lastIndexOf(DOT) + 1);
        ImageIO.write(resultImage, suffix, new File(targetFilePath));
    }

    /**
     * 缩放图片
     *
     * @param imageFilePath  源图片文件路径
     * @param targetFilePath 裁剪图片存放路径，需包含文件名
     * @param w              宽度
     * @param h              高度
     * @throws Exception 各种异常
     */
    public static void scale(String imageFilePath, String targetFilePath, int w, int h) throws Exception {
        File imageFile = new File(imageFilePath);
        BufferedImage image = ImageIO.read(imageFile);
        Image resultImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        // 将 Image转为BufferedImage
        BufferedImage tempImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = tempImage.createGraphics();
        graphics.drawImage(resultImage, 0, 0, null);
        graphics.dispose();
        // 输出
        String suffix = imageFilePath.substring(imageFilePath.lastIndexOf(DOT) + 1);
        ImageIO.write(tempImage, suffix, new File(targetFilePath));
    }

    /**
     * 水平翻转图片 镜像
     *
     * @param imageFilePath  源图片文件路径
     * @param targetFilePath 裁剪图片存放路径，需包含文件名
     * @throws Exception 各种异常
     */
    public static void inversion(String imageFilePath, String targetFilePath) throws Exception {
        File imageFile = new File(imageFilePath);
        BufferedImage image = ImageIO.read(imageFile);
        int width = image.getWidth();
        int height = image.getHeight();
        // 竟然是一维数组 不是二维
        int[] rgb = image.getRGB(0, 0, width, height, null, 0, width);
        // 翻转
        int length = width / 2;
        for (int j = 0; j < height; j++) {
            int unit = j * width;
            for (int i = 0; i < length; i++) {
                int t = rgb[unit + i];
                rgb[unit + i] = rgb[unit + width - 1 - i];
                rgb[unit + width - 1 - i] = t;
            }
        }
        image.setRGB(0, 0, width, height, rgb, 0, width);
        // 输出
        String suffix = imageFilePath.substring(imageFilePath.lastIndexOf(DOT) + 1);
        ImageIO.write(image, suffix, new File(targetFilePath));
    }

    /**
     * 旋转图片
     *
     * @param imageFilePath  源图片文件路径
     * @param targetFilePath 裁剪图片存放路径，需包含文件名
     * @param angle          顺时针旋转角度
     * @throws Exception 各种异常
     */
    public static void rotate(String imageFilePath, String targetFilePath, int angle) throws Exception {
        if (angle < 0) {
            throw new RuntimeException("参数错误");
        }
        File imageFile = new File(imageFilePath);
        BufferedImage image = ImageIO.read(imageFile);
        int width = image.getWidth();
        int height = image.getHeight();
        // 计算旋转后的画布
        Rectangle rect = calcRotatedSize(width, height, angle);
        BufferedImage resultImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resultImage.createGraphics();
        // 偏移
        graphics.translate((rect.width - width) / 2d, (rect.height - height) / 2d);
        // width/2 height/2 表示以这个坐标点为中心旋转
        graphics.rotate(Math.toRadians(angle), width / 2d, height / 2d);
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        // 输出
        String suffix = imageFilePath.substring(imageFilePath.lastIndexOf(DOT) + 1);
        ImageIO.write(resultImage, suffix, new File(targetFilePath));
    }

    /**
     * 计算旋转后的画布大小
     *
     * @param width  原图宽度
     * @param height 原图高度
     * @param angle  顺时针旋转角度
     */
    private static Rectangle calcRotatedSize(int width, int height, double angle) {
        if (angle >= 90) {
            // 180的整数倍
            if (0 == angle % 180) {
                return new Rectangle(0, 0, width, height);
                // 90的整数倍
            } else if (0 == angle % 90) {
                return new Rectangle(0, 0, height, width);
            }
            // 角度的转换
            if (angle > 360) {
                angle = angle % 360;
            }
            if (angle < 180) {
                angle = 180 - angle;
            } else if (angle > 270) {
                angle = 360 - angle;
            }
        }

        double radians = Math.toRadians(angle);
        double r = Math.sqrt(width * width + height * height) / 2;
        double len = 2 * Math.sin(radians / 2) * r;
        double angleAlpha = (Math.PI - radians) / 2;
        double angleDeltaWidth = Math.atan(height * 1.0D / width);
        double angleDeltaHeight = Math.atan(width * 1.0D / height);

        int lenDeltaWidth = (int) Math.ceil(len * Math.cos(Math.PI - angleAlpha - angleDeltaWidth));
        int lenDeltaHeight = (int) Math.ceil(len * Math.cos(Math.PI - angleAlpha - angleDeltaHeight));

        int desWidth = width + lenDeltaWidth * 2;
        int desHeight = height + lenDeltaHeight * 2;
        desWidth = desWidth > 0 ? desWidth : -desWidth;
        desHeight = desHeight > 0 ? desHeight : -desHeight;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }
}
