package com.example.privacyprotection.interceptor;

import com.example.privacyprotection.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        int check = jwtUtils.checkToken(token);
        System.out.println(token);
        System.out.println(check);
        if (check == -1) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", -1);
            map.put("msg", "token error");
            // 将数据转换为JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(map);
            // 设置响应类型和编码格式
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return false;
        }
        return true;
    }
}
