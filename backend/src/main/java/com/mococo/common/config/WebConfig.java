package com.mococo.common.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {	
	
	//크로스오리진 문제 해결하려고 넣었음
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//    	registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080");
//
//    }
}
