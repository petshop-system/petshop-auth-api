package com.petshop.auth.configuration;

import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyService;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyServiceImpl;
import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationDatabaseRepository;
import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationJPARepository;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.service.AuthenticationService;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    AuthenticationDatabaseRepository authenticationDatabaseRepository (AuthenticationJPARepository authenticationJPARepository) {
        return new AuthenticationDatabaseRepository(authenticationJPARepository);
    }

    @Bean
    AuthenticationUsercase authenticationUsercase (AuthenticationDatabaseRepository authenticationDatabaseRepository,
                                                   PasswordEncoder passwordEncoder) {
        return new AuthenticationService(authenticationDatabaseRepository,
                 passwordEncoder);
    }

    @Bean("authenticationProxyService")
    AuthenticationProxyService authenticationProxyService (AuthenticationUsercase authenticationUsercase,
                                                           AuthenticationConverterMapper authenticationConverterMapper) {
        return new AuthenticationProxyServiceImpl(authenticationUsercase, authenticationConverterMapper);
    }

}
