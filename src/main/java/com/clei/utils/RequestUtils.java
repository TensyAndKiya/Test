package com.clei.utils;

import com.alipay.api.internal.util.file.IOUtils;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * 请求工具类
 */
public class RequestUtils {
    private static SSLContext sslContext;
    public static String KEY_STORE_FILE = "D:\\test\\testISSUE.pfx";
    public static String KEY_STORE_PASS = "123456";

    /**
     * https post请求方式
     *
     * @param content
     * @param address
     * @return
     */
    public static String getHttpConnectResult(String content, String address) {
        String resultData = "";
        PrintUtil.log("http请求开始，请求地址：" + address);
        OutputStream wr = null;
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(address);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(60000);// 设置连接主机的超时时间
            conn.setReadTimeout(60000);// 设置从主机读取数据的超时时间

            SSLSocketFactory ssf = getSSLContext().getSocketFactory();
            conn.setSSLSocketFactory(ssf);
            wr = conn.getOutputStream();
            wr.write(content.getBytes("utf-8"));
            wr.flush();
            resultData = IOUtils.toString(conn.getInputStream(), "utf-8");
        } catch (MalformedURLException e) {
            PrintUtil.log("http请求失败！请求地址不正确！请求地址：" + address, e);
        } catch (IOException e) {
            PrintUtil.log("http请求失败！发生i/o错误，请求地址：" + address, e);
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                PrintUtil.log("关闭连接出错", e);
            }
        }
        return resultData;
    }


    public static SSLContext getSSLContext() {
        if (sslContext == null) {
            try {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(getkeyStore(), KEY_STORE_PASS.toCharArray());
                KeyManager[] keyManagers = kmf.getKeyManagers();
                sslContext = SSLContext.getInstance("SSL");
                TrustManager[] trustManagers = {new MyX509TrustManager()};
                sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(keyManagers, trustManagers, new SecureRandom());
                HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            } catch (Exception e) {
                PrintUtil.log("设置SSL访问出错", e);
            }
        }
        return sslContext;
    }

    public static KeyStore getkeyStore() {
        KeyStore keySotre = null;
        try {
            keySotre = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(new File(KEY_STORE_FILE));
            keySotre.load(fis, KEY_STORE_PASS.toCharArray());
            fis.close();
        } catch (Exception e) {
            PrintUtil.log("获取key文件出错", e);
        }
        return keySotre;
    }


}
