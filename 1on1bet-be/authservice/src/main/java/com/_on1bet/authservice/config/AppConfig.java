package com._on1bet.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com._on1betutils.utils1on1bet._on1BetResponseBuilder;

@Configuration
public class AppConfig {
    
    @Bean
    public _on1BetResponseBuilder on1BetResponseBuilder() {
        return new _on1BetResponseBuilder();
    }
}
