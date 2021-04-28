package com.clei.Y2019.M09.D23;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clei.utils.Base64Util;
import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;

/**
 * 百旺发票用户没收到邮件的时候 用这个下载pdf主动发送
 *
 * @author KIyA
 * @date 2019-09-23 16:50
 */
public class DownloadBaiwangInvoiceTest {

    public static void main(String[] args) throws Exception {
        String url = "http://open.hydzfp.com/open_access/buss/exec.open";
        String accessToken = "pl9w2mkI5NKnDClHHJ55ifuZMwVXOqrlLSTKa5tJKROOUTHjhOc1569227928756";
        String serviceKey = "ep_pdf_getPdfByte";
        String pdfKey = "pdf_sKj6mQm1569222122023";
        doDownload(url, accessToken, serviceKey, pdfKey);
    }

    private static void doDownload(String url, String accessToken, String serviceKey, String pdfKey) throws Exception {
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("fileKey", pdfKey);
        obj.put("access_token", accessToken);
        obj.put("serviceKey", serviceKey);
        obj.put("data", data);
        String result = OkHttpUtil.doPostJson(url, obj.toJSONString());
        JSONObject obj1 = JSON.parseObject(result);
        PrintUtil.log(obj1);
        JSONObject obj2 = JSON.parseObject(obj1.getString("rows"));
        PrintUtil.log(obj2);
        String str = obj2.getString("url");
        PrintUtil.log(str);
        Base64Util.base64ToFile(str, "D:\\invoice1.pdf");
    }

    private static void refreshAccessToken() {
        String url = "http://open.hydzfp.com/open_access/access/token.open";
        JSONObject obj = new JSONObject();
        obj.put("openid", "Ck0MxKBl5It1ln2wHqoVRRYwyIu35KZrQ09OaqhCaZDRNFr31751555312185047");
        obj.put("app_secret", "sAVCABbDO7e2cv9U60NekjfW6wd9GFtlphvV9u4DaE9kljzWH6P1555312185049");

        String result = OkHttpUtil.doPostJson(url, obj.toJSONString());
        PrintUtil.log(result);
    }
}
