package com.clei.Y2019.M11.D05;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.Base64Util;
import com.clei.utils.EncryptUtil;
import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 从日志里下载大象发票
 *
 * @author KIyA
 */
public class DownEleInvoiceTest {

    private final static String INVOICE_LINE_MARK_START = "EleInvoice Notify content : ";
    private final static String INVOICE_LINE_MARK_END = ",secretId: ";
    private final static int MARK_LENGTH = INVOICE_LINE_MARK_START.length();
    private final static String PDF_KEY = "PDF_FILE";
    private final static String SECRET_KEY = "xxx";
    private static int count = 0;

    public static void main(String[] args) throws Exception {

        String path = new File("").getAbsolutePath() + "\\";

        File file = new File(path + "all_info.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String str;
        while (null != (str = br.readLine())) {
            int index = str.indexOf(INVOICE_LINE_MARK_START);
            if (index > -1) {
                int endIndex = str.indexOf(INVOICE_LINE_MARK_END);
                str = str.substring(index + MARK_LENGTH, endIndex);
                Random random = new Random();
                String fileName = new StringBuilder(path)
                        .append(str.substring(0, 19).replaceAll(":", "-"))
                        .append('-')
                        .append(random.nextInt(10))
                        .append(random.nextInt(10))
                        .append(random.nextInt(10))
                        .append(".pdf").toString();
                PrintUtil.log(fileName);
                getInvoicePdf(str, fileName);
            }
        }
        br.close();

        PrintUtil.log("总共" + count + "个文件");
    }

    private static void getInvoicePdf(String str, String fileName) throws Exception {
        byte[] data = Base64Util.decode(str);

        data = EncryptUtil.deCompress(data);

        int endIndex = Math.min(SECRET_KEY.length(), 24);
        String des3Key = SECRET_KEY.substring(0, endIndex);

        data = EncryptUtil.decrypt3DES(data, des3Key);

        String content = new String(data, StandardCharsets.UTF_8.name());

        JSONObject json = JSONObject.parseObject(content);

        String pdfBase64Str = json.getString(PDF_KEY);

        data = Base64Util.decode(pdfBase64Str);

        Base64Util.byteToFile(data, fileName);

        PrintUtil.log(fileName);
        count++;

    }
}
