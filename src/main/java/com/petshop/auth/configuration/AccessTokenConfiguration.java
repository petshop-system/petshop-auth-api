package com.petshop.auth.configuration;

import com.petshop.auth.adapter.output.repository.database.accesstoken.AccessTokenJPARepository;
import com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenConfiguration {

    public static final String ACCESS_TOKEN_DATABASE_REPOSITORY = "accessTokenDatabaseRepository";

    @Bean(AccessTokenConfiguration.ACCESS_TOKEN_DATABASE_REPOSITORY)
    @Profile("!test")
    public AccessTokenDatabaseRepository accessTokenDatabaseRepository(AccessTokenJPARepository accessTokenJPARepository) {
        return new com.petshop.auth.adapter.output.repository.database.accesstoken
                .AccessTokenDatabaseRepository(accessTokenJPARepository);
    }

}
