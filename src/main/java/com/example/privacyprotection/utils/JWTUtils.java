package com.example.privacyprotection.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.privacyprotection.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author admin
 */
@Component
public class JWTUtils {

    private static long time = 1000 * 60 * 60 * 24 * 7;

    /**
     * 获取token
     *
     * @param user User
     * @return token
     */
    public String getToken(String username, String password) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("id", username)
                .claim("username", password)
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, "jason")
                .compact();
        return jwtToken;
//        Calendar instance = Calendar.getInstance();
//        //默认令牌过期时间7天
//        instance.add(Calendar.DATE, 7);
//
//        JWTCreator.Builder builder = JWT.create();
//        builder.withClaim("id", user.getId())
//                .withClaim("username", user.getUsername());
//
//        return builder.withExpiresAt(instance.getTime())
//                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * 验证token合法性 成功返回token
     */
//    public static DecodedJWT verify(String token) throws MyException {
//        if (StringUtils.isEmpty(token)) {
//            throw new MyException("token不能为空");
//        }
//
//        //获取登录用户真正的密码假如数据库查出来的是123456
//        String password = "admin";
//        JWTVerifier build = JWT.require(Algorithm.HMAC256(password)).build();
//        return build.verify(token);
//    }
}

