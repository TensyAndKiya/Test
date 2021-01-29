package com.clei.Y2020.M04.D16;

import com.clei.utils.Base64Util;
import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class ABCSign {

    public static void main(String[] args) throws Exception {
        // sha256();
        String str = "{\"orgSeqNo\":\"QP190528161459052249\",\"seqNo\":\"QP190528150425052249\",\"partnerId\":\"103882200110039\"}";
        String merchantCerPath = "F:\\work\\Eeparking\\ABC\\103882200110039.pfx";
        String merchantCerPassword = "abcd1234";

        PrintUtil.log(readPfxAndSha1WithRsa(str, merchantCerPath, merchantCerPassword));
    }

    /**
     * @date 2017年11月30日
     */
    public static void sha256() {
        String jsonKey = "{'key':'value'}1B4I3E3QU18C0101007F00002C32AED5";

        // java 代码实现
        PrintUtil.log("A: " + getSHA256StrJava(jsonKey));

        // apache工具类
        PrintUtil.log("B: " + getSHA256StrUseApacheCodec(jsonKey));
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制，大写
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /***
     * 利用Apache的工具类实现SHA-256加密
     *
     * @param str
     *            加密后的报文
     * @return
     */
    public static String getSHA256StrUseApacheCodec(String str) {
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    /**
     * 使用商户证书签名
     *
     * @param str
     * @param merchantCerPath
     * @param merchantCerPassword
     * @throws Exception
     */
    public static String readPfxAndSha1WithRsa(String str, String merchantCerPath, String merchantCerPassword) throws Exception {

        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(merchantCerPath);

        // If the keystore password is empty(""), then we have to set
        // to null, otherwise it won't work!!!
        char[] nPassword = null;
        if (StringUtil.isBlank(str)) {
            nPassword = null;
        } else {
            nPassword = merchantCerPassword.toCharArray();
        }
        ks.load(fis, nPassword);
        fis.close();

        // Now we loop all the aliases, we need the alias to get keys.
        // It seems that this value is the "Friendly name" field in the
        // detals tab <-- Certificate window <-- view <-- Certificate
        // Button <-- Content tab <-- Internet Options <-- Tools menu
        // In MS IE 6.
        Enumeration<String> enums = ks.aliases();
        String keyAlias = null;
        // we are readin just one certificate.
        if (enums.hasMoreElements()) {
            keyAlias = enums.nextElement();
        }

        // Now once we know the alias, we could get the keys.
        PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
        java.security.cert.Certificate cert = ks.getCertificate(keyAlias);

        // SHA1withRSA算法进行签名
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initSign(prikey);
        byte[] data = str.getBytes();
        // 更新用于签名的数据
        sign.update(data);
        byte[] signature = sign.sign();

        String result = new String(Base64.encodeBase64(signature));

        return result;
    }


    /**
     * 验签
     *
     * @param str
     * @param sign
     * @param cerPath
     */
    public static boolean readCerAndVerifySign(String str, String sign, String cerPath) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(new File(
                cerPath)));
        PublicKey publicKey = cert.getPublicKey();

        Signature verifySign = Signature.getInstance("SHA1withRSA");
        verifySign.initVerify(publicKey);
        // 用于验签的数据
        verifySign.update(str.getBytes());
        boolean flag = verifySign.verify(Base64Util.decode(sign));//验签由第三方做

        return flag;
    }
}
