package com.clei.utils;

import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * word文档操作工具类
 *
 * @author KIyA
 */
public class WordUtil {

    static {
        // 初始化操作
        setFontsFolder();
        setLicense();
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        toPdf("/usr/temp/abc.docx", "/usr/temp/abc.pdf");
        PrintUtil.log("耗时：{}ms", System.currentTimeMillis() - start);
    }

    /**
     * word -> pdf
     *
     * @param inputStream  word输入流
     * @param outputStream pdf输出流
     * @throws Exception
     */
    public void toPdf(InputStream inputStream, OutputStream outputStream) throws Exception {
        // 转换
        Document doc = new Document(inputStream);
        doc.save(outputStream, SaveFormat.PDF);
    }

    /**
     * word -> pdf
     *
     * @param wordFile word目录
     * @param pdfFile  输出pdf目录
     * @throws Exception
     */
    public static void toPdf(String wordFile, String pdfFile) throws Exception {
        Document doc = new Document(wordFile);
        doc.save(new FileOutputStream(new File(pdfFile)), SaveFormat.PDF);
    }

    /**
     * linux系统没安装所需的字体文件的话，可以新建个目录将windows字体传到该目录
     */
    private static void setFontsFolder() {
        boolean linuxSystem = false;
        if (linuxSystem) {
            // 初始化使用的设置字体文件目录
            FontSettings.setFontsFolder("/usr/share/fonts/win", true);
        }
    }

    /**
     * 设置license 不然有水印
     *
     * @throws Exception
     */
    private static void setLicense() {
        // licenseStr
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<License>\n" +
                "<Data>\n" +
                "\t<Products>\n" +
                "\t\t<Product>Aspose.Total for Java</Product>\n" +
                "\t\t<Product>Aspose.Words for Java</Product>\n" +
                "\t</Products>\n" +
                "\t<EditionType>Enterprise</EditionType>\n" +
                "\t<SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                "\t<LicenseExpiry>20991231</LicenseExpiry>\n" +
                "\t<SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                "</Data>\n" +
                "<Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                "</License>";
        // license
        License license = new License();

        try {
            license.setLicense(new ByteArrayInputStream(str.getBytes()));
        } catch (Exception e) {
            PrintUtil.log("设置license出错", e);
        }
    }
}
