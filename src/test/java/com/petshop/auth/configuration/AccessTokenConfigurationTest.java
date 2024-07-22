package com.petshop.auth.configuration;

import com.petshop.auth.adapter.output.repository.database.accesstoken.AccessTokenJPARepository;
import com.petshop.auth.application.domain.AccessTokenDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@TestConfiguration
@Profile("test")
public class AccessTokenConfigurationTest {

    @Bean(AccessTokenConfiguration.ACCESS_TOKEN_DATABASE_REPOSITORY)
    public AccessTokenDatabaseRepository accessTokenDatabaseRepository() {
        return new AccessTokenDatabaseRepository() {
            @Override
            public AccessTokenDomain save(AuthenticationDomain authenticationDomain, String token) throws Exception {
                return null;
            }
        };
    }

}
