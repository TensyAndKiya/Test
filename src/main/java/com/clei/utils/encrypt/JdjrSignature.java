package com.clei.utils.encrypt;

import com.clei.utils.PrintUtil;
import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author lanchunqiu
 * @version V1.0.0
 * @date 2020/1/11 09:18
 */
public class JdjrSignature {

    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    public static final String DEFAULT_CHARSET = "utf-8";

    /**
     * @param params
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(Map<String, String> params, String privateKey) throws Exception {
        String content = getSignCheckContent(params);
        return rsa256Sign(content, privateKey);
    }

    /**
     * @param params
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean check(Map<String, String> params, String publicKey) throws Exception {
        String sign = params.get("sign");
        String content = getSignCheckContent(params);
        return rsa256CheckContent(content, sign, publicKey);
    }

    /**
     * sha256WithRsa
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static String rsa256Sign(String content, String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
        byte[] encodedKey = privateKey.getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_SHA256RSA_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(content.getBytes(DEFAULT_CHARSET));
        byte[] signed = signature.sign();
        return new String(Base64.encodeBase64(signed));
    }

    /**
     * sign
     * &
     *
     * @param params
     * @return
     */
    private static String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        }
        params.remove("sign");
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (isBlank(value)) {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        return content.toString();
    }

    private static boolean rsa256CheckContent(String content, String sign, String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
        byte[] encodedKey = publicKey.getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_SHA256RSA_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes(DEFAULT_CHARSET));
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    private static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * @return
     */
    public static KeyPairs generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SIGN_TYPE_RSA);
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            KeyPairs keyPairs = new KeyPairs(Base64.encodeBase64String(publicKey.getEncoded()), Base64.
                    encodeBase64String(privateKey.getEncoded()));
            return keyPairs;
        } catch (NoSuchAlgorithmException e) {
            PrintUtil.log("未找到指定算法", e);
        }
        return null;
    }

    public static class KeyPairs {
        private String publicKey;
        private String privateKey;

        public KeyPairs(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }

    //
    public static void main(String[] args) throws Exception {
        KeyPairs keyPairs = generateKey();
        Map<String, String> params = new HashMap<>();
        params.put("appId", "appId");
        params.put("bizParam", "{\"actKey\":\"N32MNf\",\"ip\":\"127.0.0.1\",\"phone\":\"18200000000\"}");
        PrintUtil.log("私钥：" + keyPairs.getPrivateKey());
        PrintUtil.log("公钥：" + keyPairs.getPublicKey());
        String sign = JdjrSignature.sign(params, keyPairs.getPrivateKey());
        params.put("sign", sign);
        boolean result = JdjrSignature.check(params, keyPairs.getPublicKey());
        assert result;
    }
}
