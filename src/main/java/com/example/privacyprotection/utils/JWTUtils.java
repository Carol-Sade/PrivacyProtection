package com.example.privacyprotection.utils;


import com.example.privacyprotection.service.UserService;
import io.jsonwebtoken.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
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

    public String getToken(int id, String username) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("id", id)
                .claim("username", username)
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

    public int cleanToken(String token) {
        if (Boolean.TRUE.equals(redisTemplate.delete(token))) {
            return 1;
        } else {
            return 0;
        }
    }
}

