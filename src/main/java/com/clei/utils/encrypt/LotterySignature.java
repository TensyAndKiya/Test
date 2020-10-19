package com.clei.utils.encrypt;

import com.clei.utils.PrintUtil;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotterySignature {

    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    public static final String DEFAULT_CHARSET = "utf-8";

    /**
     * 签名
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
     * 验签
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
     * sha256WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static String rsa256Sign(String content, String privateKey) throws Exception {


        KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);

        byte[] encodedKey = privateKey.getBytes();

        encodedKey = Base64.encodeBase64(encodedKey);

        PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));

        Signature signature = Signature
                .getInstance(SIGN_SHA256RSA_ALGORITHMS);

        signature.initSign(priKey);

        signature.update(content.getBytes(DEFAULT_CHARSET));

        byte[] signed = signature.sign();

        return new String(Base64.encodeBase64(signed));

    }

    /**
     * 剔除sign参数，剔除值为空的参数
     * 所有参数按参数名，字典序排序，并用&符号连接
     *
     * @param params 待签名或验签参数
     * @return 待签名或验签字符串
     */
    private static String getSignCheckContent(Map<String/*参数名*/, String/*参数值*/> params) {
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
     * 生成密钥对
     *
     * @return
     */
    public static KeyPairs generateKey() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(SIGN_TYPE_RSA);
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到私钥字符串
            KeyPairs keyPairs = new KeyPairs(Base64.encodeBase64String(publicKey.getEncoded()), Base64.encodeBase64String(privateKey.getEncoded()));

            return keyPairs;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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

    public static void main(String[] args) throws Exception {
        KeyPairs keyPairs = generateKey();


        PrintUtil.dateLine(keyPairs.getPrivateKey());
        PrintUtil.dateLine(keyPairs.getPublicKey());

        keyPairs.setPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIQFMXCV44Uj7BG20VRKH3RdCBsSj9OsYOmWE9TxOjXeCdRogB1RW+2qXPvrdRv4ufbosefxfKoKHNc6URaYESTQHg8wX3ieEhe8PLXZ8imN7t05yoHql3zpbJ2f1ju0JvUMt/NEOz3tnxlRpbRJEaxgKxBLP6If9v+gFvHbekfWW/B+i/4QKVNyFssEzn2Zf4LjQnXkFdqf+0w00//IRaQgoHTj9xI5XufSHJZ8erO5Sn4AizY0gKFI287jso8Y1ccI3zVM5dKcn9PjmRQy8iKKISyFx00+K2pzWsIu2rUkbhYDaFPDD6e5VXWI4fcyIH0WW3/rBMVZBk9zfeDCtdAgMBAAECggEABH4if0L20WKc4R83ZJkjwhSSQISH9nmmqDqUg0IFQsDItEbsGytckTOCfiwqvU5Um0g43COStELBsxWaHs3czK6vXiyFSNlKDCGZni4THj/uMedlZgHE0seEH1nJyi0VjrgaTbJ6fQbfJuEkwlerdTeMRSwDhz7xvk5/F5viP1E61GNXrjv+tHueqNC+jHeY6cZNgJ4ACjhrwsRBve4aK6Zr1MxVfV4Xw5QXhKUE7CaG/8QijXN0Fn5DNO12DfFmSdDHbHefFd5go0ZY5UnKQonUk/4yvBG23soeXN/OwIbMgE8Dqkghwh1jh0woBdOu36ZN/lctfmmMcDodBFLR4QKBgQDPTghv5KV/mXUj6/1bH9qlm0RlRd+jVbAmf/bY0p/RQnEY9Dx/vlE5zMaDc4EnH1Cm1yC5p7Sx6b1a7ouzYCCxkyuN3EPMyjlQmh+9D6LiMX/LrQkmIoA3QiA64f2cBcQjwOXHE0GhhWVhsERy5DSgio+WMJuIReMAk2IfHCZk+QKBgQCoQZeSQfqZylHoILISc8A8ANdp1z9HpR9nVyap81zsshqwAw6ieS3qrrPDkHoMF2okJ81eQg5cFd/Sl1avqKiNsXNKkxexbzoZPGIb4hfD9Qgv28NkBFMVqEwVzNmqN8rn4d5NpePPhFnjBBwfvCrgVtqNd9kqcWpJelPprPLmhQKBgCgGC9D2lxFMonYeANtQChnBIXJgDC5vw8ObHyB7gcmYB1fnB6suJmZ/Z7BHY58XB64+iJ3viA51b06YEba/D3DZViZcaWangGtUZl5NH4iGQAMl1Eddj92bUbesnBc8ccLpYjOOPzSdGZbDvmNkNdjtB43aspybyZj5247gE3WZAoGBAKa2lyBsD4piVVXTT53I4yiRDN/qTuEGU19pkv3CDMjakGbmIJILz9tAzw1vEf75FEpOxcYnXWMQqX1YRqQ2UQxUTxANPuSoeMCYe/10wGeBWmNRNQDc6BYFc54GI4XtUjcALexx+o+bbBTr9ZHcN/+hD1ws7BiJ1+6GLq7SlrqhAoGACrsBZdRZ3UffyL95YIDKOQZ7cgyPd6iLPn9Oe4ELrZHKHGEa+tG1HkSczcYgCjq25+h8QZ+5+jObNOcvkQA7WWQNuJ6kPpqk/gisMrrJ9gr6Pdb4NdtxE4xwJoMZ8Nbwy0udHfq0UudnJGAi00yRoKx+6OlqsdlDbhhETqFv8p8=");
        keyPairs.setPrivateKey(keyPairs.getPrivateKey().replaceAll("\r", "").replaceAll("\n", ""));


        keyPairs.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiEBTFwleOFI+wRttFUSh90XQgbEo/TrGDplhPU8To13gnUaIAdUVvtqlz763Ub+Ln26LHn8XyqChzXOlEWmBEk0B4PMF94nhIXvDy12fIpje7dOcqB6pd86Wydn9Y7tCb1DLfzRDs97Z8ZUaW0SRGsYCsQSz+iH/b/oBbx23pH1lvwfov+EClTchbLBM59mX+C40J15BXan/tMNNP/yEWkIKB04/cSOV7n0hyWfHqzuUp+AIs2NIChSNvO47KPGNXHCN81TOXSnJ/T45kUMvIiiiEshcdNPitqc1rCLtq1JG4WA2hTww+nuVV1iOH3MiB9Flt/6wTFWQZPc33gwrXQIDAQAB");
        keyPairs.setPublicKey(keyPairs.getPublicKey().replaceAll("\r", "").replaceAll("\n", ""));

        PrintUtil.dateLine(keyPairs.getPrivateKey());
        PrintUtil.dateLine(keyPairs.getPublicKey());

        //签名
        Map<String, String> params = new HashMap<>();
        params.put("appId", "appId");
        params.put("bizParam", "{\"actKey\":\"N32MNf\",\"ip\":\"127.0.0.1\",\"phone\":\"18200000000\"}");

        //验证签名
        String sign = LotterySignature.sign(params, keyPairs.getPrivateKey());
        params.put("sign", sign);

        boolean result = LotterySignature.check(params, keyPairs.getPublicKey());

    }

}
