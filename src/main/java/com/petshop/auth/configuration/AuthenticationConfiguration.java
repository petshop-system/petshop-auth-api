package com.petshop.auth.configuration;

import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyService;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyServiceImpl;
import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationDatabaseRepository;
import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationJPARepository;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.service.AuthenticationService;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationConfiguration {

    public static final String AUTHENTICATION_PROXY_SERVICE = "authenticationProxyService";

    public static final String AUTHENTICATION_DATABASE_REPOSITORY = "authenticationDatabaseRepository";

    public static final String AUTHENTICATION_USERCASE = "authenticationUsercase";

    @Bean(AuthenticationConfiguration.AUTHENTICATION_DATABASE_REPOSITORY)
    @Profile("!test")
    com.petshop.auth.application.port.output.repository.AuthenticationDatabaseRepository authenticationDatabaseRepository (AuthenticationJPARepository authenticationJPARepository) {
        return new AuthenticationDatabaseRepository(authenticationJPARepository);
    }

    @Bean(AuthenticationConfiguration.AUTHENTICATION_USERCASE)
    AuthenticationUsercase authenticationUsercase (
            com.petshop.auth.application.port.output.repository.AuthenticationDatabaseRepository authenticationDatabaseRepository,
                                                   PasswordEncoder passwordEncoder) {
        return new AuthenticationService(authenticationDatabaseRepository,
                 passwordEncoder);
    }

    @Bean(AuthenticationConfiguration.AUTHENTICATION_PROXY_SERVICE)
    AuthenticationProxyService authenticationProxyService (AuthenticationUsercase authenticationUsercase,
                                                           AuthenticationConverterMapper authenticationConverterMapper) {
        return new AuthenticationProxyServiceImpl(authenticationUsercase, authenticationConverterMapper);
    }

}
