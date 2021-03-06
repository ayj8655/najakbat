package com.mococo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.addAllowedOrigin("http://localhost:3000");
      config.addAllowedOrigin("http://192.168.45.109:3000");
      config.addAllowedOrigin("http://i5b203.p.ssafy.io:3000");
      config.addAllowedOrigin("http://i5b203.p.ssafy.io");
      config.addAllowedOrigin("http://3.38.38.20");
      config.addAllowedHeader("*");
      config.addAllowedMethod("*");
      
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }

}