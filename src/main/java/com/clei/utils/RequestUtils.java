package com.clei.utils;

import com.alipay.api.internal.util.file.IOUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


/**
 * 请求工具类
 *
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
		PrintUtil.dateLine("http请求开始，请求地址：" + address);
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
			e.printStackTrace();
			PrintUtil.dateLine("http请求失败！请求地址不正确！请求地址：" + address);
		} catch (IOException e) {
			e.printStackTrace();
			PrintUtil.dateLine("http请求失败！发生i/o错误，请求地址：" + address);
		} finally {
			try {
				if (wr != null) {
					wr.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
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
				sslContext=SSLContext.getInstance("SSL");
				TrustManager[] trustManagers={new MyX509TrustManager()};
				sslContext = SSLContext.getInstance("TLSv1.2");
				sslContext.init(keyManagers, trustManagers, new SecureRandom());
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
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
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keySotre;
	}


}
