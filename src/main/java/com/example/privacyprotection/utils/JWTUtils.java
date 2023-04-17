package com.example.privacyprotection.utils;


import io.jsonwebtoken.*;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author admin
 */
@Component
public class JWTUtils {

    private static final long time = 1000 * 60 * 60 * 24 * 7;
    private static final String signature = "jason";

    public String getToken(String username, String password) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", username)
                .claim("password", password)
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }

    public String checkToken(String token) {
        if (token == null) {
            return null;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
//            System.out.println(claims.getId());
//            System.out.println(claims.get("username"));
//            System.out.println(claims.get("password"));
//            System.out.println(claims.getExpiration());
            return (String) claims.get("username");
        } catch (Exception e) {
            return "-1";
        }
    }
}

