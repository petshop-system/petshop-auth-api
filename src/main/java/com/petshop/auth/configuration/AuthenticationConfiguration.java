package com.petshop.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.output.repository.cache.authentication.AuthenticationRedisRepository;
import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationDatabaseRepository;
import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationJPARepository;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.port.output.repository.AuthenticationCacheRepository;
import com.petshop.auth.application.service.AuthenticationService;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;
import com.petshop.auth.utils.converter.AuthenticationConverterMapperImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationConfiguration {

    @Bean("authenticationConverterMapper")
    AuthenticationConverterMapper authenticationConverterMapper () {
        return new AuthenticationConverterMapperImpl();
    }

    @Bean
    AuthenticationDatabaseRepository authenticationDatabaseRepository (AuthenticationJPARepository authenticationJPARepository) {
        return new AuthenticationDatabaseRepository(authenticationJPARepository);
    }

    @Bean
    AuthenticationCacheRepository authenticationCacheRepository(AuthenticationRedisRepository authenticationRedisRepository,
                                                                ObjectMapper objectMapper,
                                                                @Qualifier("authenticationConverterMapper") AuthenticationConverterMapper converterMapper){
        return new com.petshop.auth.adapter.output.repository.cache.authentication.AuthenticationCacheRepository(authenticationRedisRepository,
                objectMapper, converterMapper);
    }

    @Bean
    AuthenticationUsercase authenticationUsercase (AuthenticationDatabaseRepository authenticationDatabaseRepository,
                                                   AuthenticationCacheRepository authenticationCacheRepository,
                                                   PasswordEncoder passwordEncoder) {
        return new AuthenticationService(authenticationCacheRepository, authenticationDatabaseRepository,
                 passwordEncoder);
    }

}
