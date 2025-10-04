package com.knowledge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 *
 * @author system
 * @date 2025-10-04
 */
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有域名进行跨域调用
        config.addAllowedOrigin("*");
        // 注意：当使用 "*" 通配符时，不能设置 setAllowCredentials(true)
        // 如果需要发送cookie，请明确指定允许的域名，例如：
        // config.addAllowedOrigin("http://localhost:5173");
        // config.setAllowCredentials(true);
        
        // 允许所有请求方法
        config.addAllowedMethod("*");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 暴露所有响应头
        config.addExposedHeader("*");
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

