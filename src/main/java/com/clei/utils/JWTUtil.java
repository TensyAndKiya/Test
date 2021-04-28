package com.clei.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON WEB TOKEN 工具类
 *
 * @author KIyA
 */
public class JWTUtil {

    private final static String SECRET = "linkinpark";
    private final static String ISSUER = "kiya";
    private final static byte[] SECRET_BYTE_DATA = DatatypeConverter.parseBase64Binary(SECRET);

    private final static int EXPIRE_MILLS = 24 * 60 * 60 * 1000;

    public static String createToken(Object obj) {
        Map<String, Object> header = new HashMap<>(4);
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("obj", obj);
        Date curDate = new Date();
        Date expireDate = new Date(curDate.getTime() + EXPIRE_MILLS);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signKey = new SecretKeySpec(SECRET_BYTE_DATA, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParams(header)
                .addClaims(claims)
                .setIssuedAt(curDate)
                .setExpiration(expireDate)
                .setIssuer(ISSUER)
                .signWith(signatureAlgorithm, signKey);
        return jwtBuilder.compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_BYTE_DATA).parseClaimsJws(token).getBody();
    }
}
