package com.hoffmann_g.security_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class AppConfig {
    
    @Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
