package com.clei.Y2019.M08.D07;

import com.clei.utils.Base64Util;
import com.clei.utils.EncryptUtil;
import com.clei.utils.PrintUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 内容比较少的话压缩后字节数还变多了。。
 * 内容较多的话压缩才是真滴压缩
 *
 * @author KIyA
 */
public class ZipTest {

    private final static String CHARSET_UTF8 = StandardCharsets.UTF_8.name();

    public static void main(String[] args) throws Exception {
        String str = "我是你滴大爷爷呀！";
        PrintUtil.log("原文 ：" + str);
        String zipStr = zip(str);
        PrintUtil.log("压缩后 ：" + zipStr);
        PrintUtil.log("解压后 ：" + unZip(zipStr));

        byte[] zipData = EncryptUtil.compress(str.getBytes(CHARSET_UTF8));
        byte[] unzipData = EncryptUtil.deCompress(zipData);
        zipStr = Base64Util.encode(zipData);
        PrintUtil.log("压缩后 ：" + zipStr);
        PrintUtil.log("解压后 ：" + new String(unzipData, CHARSET_UTF8));
    }

    private static String zip(String str) throws Exception {
        byte[] bytes = str.getBytes(CHARSET_UTF8);
        PrintUtil.log(bytes.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        ZipEntry zipEntry = new ZipEntry("zip");
        zipEntry.setSize(bytes.length);
        zos.putNextEntry(zipEntry);
        zos.write(bytes);
        zos.closeEntry();
        bytes = bos.toByteArray();
        PrintUtil.log(bytes.length);
        String result = Base64Util.encode(bytes);
        bos.close();
        return result;
    }

    private static String unZip(String str) throws Exception {
        byte[] bytes = Base64Util.decode(str);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ZipInputStream zis = new ZipInputStream(bis);
        byte[] byteResult = null;
        while (zis.getNextEntry() != null) {
            byte[] buf = new byte[1024];
            int leng;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((leng = zis.read(buf, 0, 1024)) != -1) {
                bos.write(buf, 0, leng);
            }
            byteResult = bos.toByteArray();
            bos.flush();
            bos.close();
        }
        zis.close();
        bis.close();
        return new String(byteResult, StandardCharsets.UTF_8);
    }
}
