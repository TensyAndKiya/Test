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

public class JWTUtil {
    public static String secret = "linkinpark";
    public static String issuer = "kiya";

    private final static int EXPIRE_MILLS = 24 * 60 * 60 * 1000;

    public static String createToken(Object obj){
        Map<String,Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        Map<String,Object> claims = new HashMap<>();
        claims.put("obj",obj);
        Date curDate = new Date();
        Date expireDate = new Date(curDate.getTime() + EXPIRE_MILLS);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signKey = new SecretKeySpec(secretBytes,signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder()
                                    .setHeaderParams(header)
                                    .addClaims(claims)
                                    .setIssuedAt(curDate)
                                    .setExpiration(expireDate)
                                    .setIssuer(issuer)
                                    .signWith(signatureAlgorithm,signKey);

        String token = jwtBuilder.compact();
        return token;
    }

    public static Claims parseToken(String token){
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                            .parseClaimsJws(token).getBody();
        return claims;
    }
}
