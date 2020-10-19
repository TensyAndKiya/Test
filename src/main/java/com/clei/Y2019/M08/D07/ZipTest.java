package com.clei.Y2019.M08.D07;

import com.clei.utils.PrintUtil;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * 内容比较少的话压缩后字节数还变多了。。
 * 内容较多的话压缩才是真滴压缩
 */
public class ZipTest {
    private final static String CHARSET_UTF8 = "UTF-8";
    public static void main(String[] args) throws Exception {
        String str = "我是你滴大爷爷呀！";
        PrintUtil.dateLine("原文 ：" + str);
        String zipStr = zip(str);
        PrintUtil.dateLine("压缩后 ：" + zipStr);
        PrintUtil.dateLine("解压后 ：" + unZip(zipStr));
    }

    private static String zip(String str) throws Exception{
        byte[] bytes = str.getBytes(CHARSET_UTF8);
        PrintUtil.dateLine(bytes.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        ZipEntry zipEntry = new ZipEntry("zip");
        zipEntry.setSize(bytes.length);
        zos.putNextEntry(zipEntry);
        zos.write(bytes);
        zos.closeEntry();
        bytes = bos.toByteArray();
        PrintUtil.dateLine(bytes.length);
        String result = new Base64Encoder().encode(bytes);
        bos.close();
        return result;
    }

    private static String unZip(String str) throws Exception{
        byte[] bytes = new BASE64Decoder().decodeBuffer(str);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ZipInputStream zis = new ZipInputStream(bis);
        byte[] byteResult = null;
        while (zis.getNextEntry() != null){
            byte[] buf = new byte[1024];
            int leng;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while( (leng = zis.read(buf,0,1024)) != -1 ){
                bos.write(buf,0,leng);
            }
            byteResult = bos.toByteArray();
            bos.flush();
            bos.close();
        }
        zis.close();
        bis.close();
        String result = new String(byteResult,CHARSET_UTF8);
        return result;
    }
}
