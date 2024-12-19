package com.doan.trainbooker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//	}
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Cho phép từ frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các method cho phép
                .allowCredentials(true); // Nếu có sử dụng cookie
    }
}
