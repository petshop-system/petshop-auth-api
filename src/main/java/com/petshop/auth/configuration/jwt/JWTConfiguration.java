package com.petshop.auth.configuration.jwt;

import com.petshop.auth.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfiguration {

    @Bean("jwtProperties")
    @ConfigurationProperties("jwt.app")
    JWTProperties jwtProperties() {
        return new JWTProperties();
    }

    @Bean("jwtUtils")
    public JWTUtils jwtUtils (@Qualifier("jwtProperties") JWTProperties jwtProperties) {
        return new JWTUtils(jwtProperties);
    }
}
