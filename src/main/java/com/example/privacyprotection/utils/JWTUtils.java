package com.example.privacyprotection.utils;


import com.example.privacyprotection.entity.User;
import io.jsonwebtoken.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
@Component
public class JWTUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long time = 1000 * 60 * 60 * 24 * 7;
    private static final String signature = "jason";

    public String getToken(User user) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        redisTemplate.opsForValue().set(jwtToken, true, 7, TimeUnit.DAYS);
        return jwtToken;
    }

    public int checkToken(String token) {
        if (token == null) {
            return -1;
        }
        Object redisCheck = redisTemplate.opsForValue().get(token);
        if (redisCheck == null) {
            return -1;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
//            System.out.println(claims.getId());
//            System.out.println(claims.get("username"));
//            System.out.println(claims.get("password"));
//            System.out.println(claims.getExpiration());
            return (int) claims.get("id");
        } catch (Exception e) {
            return -1;
        }
    }

    public Map<String, Object> checkPermission(String token) {
        Map<String, Object> map = new HashMap<>();
        if (token == null) {
            map.put("code", -1);
            return map;
        }
        Object redisCheck = redisTemplate.opsForValue().get(token);
        if (redisCheck == null) {
            map.put("code", -1);
            return map;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
//            System.out.println(claims.getId());
//            System.out.println(claims.get("username"));
//            System.out.println(claims.get("password"));
//            System.out.println(claims.getExpiration());
            map.put("id", (int) claims.get("id"));
            map.put("role", (int) claims.get("role"));
            return map;
        } catch (Exception e) {
            map.put("code", -1);
            return map;
        }
    }

    public int cleanToken(String token) {
        if (Boolean.TRUE.equals(redisTemplate.delete(token))) {
            return 1;
        } else {
            return 0;
        }
    }

//    @Test
//    public void printToken() {
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MzAsInVzZXJuYW1lIjoiMTIzNDU2IiwiZXhwIjoxNjgyNTI4OTM5LCJqdGkiOiI2ZTk0NzBiYS1kM2FmLTRlNGItYThiNy1hMDRjYThmMjI4YTAifQ.c-i8V-CrGodcYV1eB2T2RsFX8edAwFYtF3iQkpaor7s";
////        Object redisCheck = redisTemplate.opsForValue().get(token);
////        System.out.println(redisCheck);
////        if (redisCheck == null) {
////            System.out.println("token is null");
////        }
//        try {
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
//            Claims claims = claimsJws.getBody();
//            System.out.println(claims.getId());
//            System.out.println(claims.get("id"));
//            System.out.println(claims.get("username"));
//            System.out.println(claims.getExpiration());
//        } catch (Exception e) {
//            System.out.println("jwt error");
//        }
//    }
}

