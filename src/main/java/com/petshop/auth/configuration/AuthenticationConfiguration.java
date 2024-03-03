package com.petshop.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.output.repository.cache.AuthenticationRedisRepository;
import com.petshop.auth.adapter.output.repository.database.AuthenticationDatabaseRepository;
import com.petshop.auth.adapter.output.repository.database.AuthenticationJPARepository;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.port.output.repository.AuthenticationCacheRepository;
import com.petshop.auth.application.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    AuthenticationDatabaseRepository authenticationDatabaseRepository (AuthenticationJPARepository authenticationJPARepository) {
        return new com.petshop.auth.adapter.output.repository.database.AuthenticationDatabaseRepository(authenticationJPARepository);
    }

    @Bean
    AuthenticationCacheRepository authenticationCacheRepository(AuthenticationRedisRepository authenticationRedisRepository,
                                                                ObjectMapper objectMapper){
        return new com.petshop.auth.adapter.output.repository.cache.AuthenticationCacheRepository(authenticationRedisRepository, objectMapper);
    }

    @Bean
    AuthenticationUsercase authenticationUsercase (AuthenticationDatabaseRepository authenticationDatabaseRepository,
                                                   AuthenticationCacheRepository authenticationCacheRepository,
                                                   PasswordEncoder passwordEncoder) {
        return new AuthenticationService(authenticationCacheRepository, authenticationDatabaseRepository,
                 passwordEncoder);
    }

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder(10);
    }
}