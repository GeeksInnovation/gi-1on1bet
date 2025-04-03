package com._on1bet.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com._on1bet.authservice.repo.UtilRepo;
import com._on1bet.authservice.repo.UtilRepoImpl;
import com._on1betutils.utils1on1bet._on1BetResponseBuilder;

@Configuration
public class AppConfig {

    private final DatabaseClient databaseClient;

    public AppConfig(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Bean
    public _on1BetResponseBuilder on1BetResponseBuilder() {
        return new _on1BetResponseBuilder();
    }

    @Bean
    public UtilRepo utilRepo() {
        return new UtilRepoImpl(databaseClient);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
