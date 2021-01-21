package com.clei.Y2019.M11.D05;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.Base64Util;
import com.clei.utils.PrintUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Random;
import java.util.zip.GZIPInputStream;

public class DownEleInvoiceTest {

    private final static String INVOICE_LINE_MARK = "EleInvoice Notify content : ";
    private final static int MARK_LENGTH = INVOICE_LINE_MARK.length();
    private final static String PDF_KEY = "PDF_FILE";
    private final static String SECRET_KEY = "xxx";
    private final static String ALGORITHM = "DESede";
    private final static String CHARSET_UTF8 = "UTF-8";
    private final static int BUFFER = 4096;
    private static int count = 0;

    public static void main(String[] args) throws Exception{

        String path = new File("").getAbsolutePath() + "\\";

        File file = new File(path + "all_info.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),CHARSET_UTF8));
        String str;
        while(null != (str = br.readLine())){
            int index = str.indexOf(INVOICE_LINE_MARK);
            if(index > -1){
                String fileName = path + str.substring(0,19).replaceAll(":","-") + "_";
                int endIndex = str.indexOf(",secretId:");
                str = str.substring(index + MARK_LENGTH,endIndex);
                Random random = new Random();
                fileName = fileName + "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + ".pdf";
                PrintUtil.dateLine(fileName);
                getInvoicePdf(str, fileName);
            }
        }
        br.close();

        PrintUtil.dateLine("总共" + count + "个文件");
    }

    private static void getInvoicePdf(String str,String fileName) throws Exception {
        byte[] data = Base64Util.decode(str);

        data = deCompress(data);

        String des3Key = SECRET_KEY.substring(0,24);

        data = decrypt3DES(data,des3Key,CHARSET_UTF8);

        String content = new String(data,CHARSET_UTF8);

        JSONObject json = JSONObject.parseObject(content);

        String pdfBase64Str = json.getString(PDF_KEY);

        data = Base64Util.decode(pdfBase64Str);

        byteToFile(data,fileName);

        PrintUtil.dateLine(fileName);
        count++;

    }

    private static byte[] deCompress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        deCompress(bais,baos);
        data = baos.toByteArray();
        baos.flush();
        baos.close();
        return data;
    }

    public static void deCompress(InputStream is,OutputStream os) throws Exception {
        GZIPInputStream gis = new GZIPInputStream(is);
        int length;
        byte[] data = new byte[BUFFER];
        while((length = gis.read(data,0,BUFFER)) != -1){
            os.write(data,0,length);
        }
        gis.close();
        is.close();
    }

    public static byte[] decrypt3DES(byte[] bytes,String key,String charset) throws Exception{
        // key
        SecretKey secretKey = new SecretKeySpec(key.getBytes(charset),ALGORITHM);
        // 解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);

        return cipher.doFinal(bytes);
    }

    public static void byteToFile(byte[] bytes,String filePath) throws Exception {
        File file = new File(filePath);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(bytes);
        bos.flush();
        bos.close();
    }
}
