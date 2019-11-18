package com.clei.utils;

import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;
import java.util.Scanner;

public class DownloadEleInvoiceUtil {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        String content = input.nextLine();
        String fileName = DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd");
        fileName = fileName + "\\" + input.nextLine() + ".pdf";
        String notifyStr = notify(content, "", "1", "1");
        JSONObject json = JSONObject.parseObject(notifyStr);
        json.forEach((k, v) -> System.out.println(k + " " + v.toString().length()));
        Base64Util.base64ToFile(json.getString("PDF_FILE"), "D:\\files\\" + fileName);
    }

    private static String notify(String content, String secretId, String encryptCode, String zipCode) throws Exception {
        byte[] data = Base64Util.base64decode(content);
        // 解压缩
        if ("1".equals(zipCode)) {
            data = EncryptUtil.deCompress(data);
        }
        // 解密
        if ("1".equals(encryptCode)) {
            String secretKey = "fe5b568cbb2f46d4820dfe1c488ef1b6";
            // 加密key为secretKey前24位
            String desKey = secretKey.substring(0, 24);
            data = EncryptUtil.decrypt3DES(data, desKey, null);
        }
        return new String(data, "UTF-8");
    }
}

