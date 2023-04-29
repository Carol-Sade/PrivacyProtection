package com.example.privacyprotection.config;

import com.example.privacyprotection.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    String[] addPathPatterns = {
            "/api/**"
    };
    String[] excludePathPatterns = {
            "/api/user/login", "/api/user/register", "/api/user/logout"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(authInterceptor).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
