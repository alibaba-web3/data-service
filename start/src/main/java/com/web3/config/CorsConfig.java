package com.web3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author fuxian
 * @Date 2023/3/13
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry // 所有接口都支持跨域
                .addMapping("/**")
                // 允许访问的地址
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                // 允许访问的 Rest 方法 "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" ...
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }

}
