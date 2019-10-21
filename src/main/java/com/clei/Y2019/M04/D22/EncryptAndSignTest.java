package com.clei.Y2019.M04.D22;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class EncryptAndSignTest {
    public static void main(String[] args) throws Exception{
        //sign: RSA SHA-256
        //encrypt: RC4 DES
        getKeyPair();
    }

    private static Map<String,Object> getKeyPair() throws NoSuchAlgorithmException {
        if(true){
            return null;
        }
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String,Object> keyMap = new HashMap<>(2);
        keyMap.put("publicKey",publicKey);
        keyMap.put("privateKey",privateKey);
        return keyMap;

    }
}
