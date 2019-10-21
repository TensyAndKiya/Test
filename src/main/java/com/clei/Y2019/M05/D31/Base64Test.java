package com.clei.Y2019.M05.D31;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Base64Test {
    private static String IMG_PATH = "D:\\img\\img.jpg";
    public static void main(String[] args) {
        String imgStr = imgEncode(IMG_PATH);
        System.out.println(imgStr);
        System.out.println(imgDecode(imgStr));
    }

    private static String imgEncode(String imgPath){
        InputStream inputStream = null;
        byte[] data = null;
        try{
            inputStream = new FileInputStream(imgPath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != inputStream){
                try{
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    private static String imgDecode(String imgStr){
        String imgPath = "D:\\img\\img2.jpg";
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream outputStream = null;
        try{
            byte[] data = decoder.decodeBuffer(imgStr);
            if(null != data && data.length > 0){
                for (int i = 0; i < data.length; i++) {
                    if(data[i] < 0){
                        data[i] += 256;
                    }
                }
                outputStream = new FileOutputStream(imgPath);
                outputStream.write(data);
                //如果使用buffer的话，flush走起
                //outputStream.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != outputStream){
                try{
                    outputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return imgPath;
    }
}
