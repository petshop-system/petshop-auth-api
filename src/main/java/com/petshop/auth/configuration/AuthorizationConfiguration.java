package com.petshop.auth.configuration;

import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.application.port.output.repository.AccessTokenCacheRepository;
import com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository;
import com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository;
import com.petshop.auth.application.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationConfiguration {

    @Bean("authorizationUserCase")
    AuthorizationUserCase authorizationUserCase(@Qualifier("accessTokenDatabaseRepository") AccessTokenDatabaseRepository accessTokenDatabaseRepository,
                                                @Qualifier("accessTokenCacheRepository") AccessTokenCacheRepository accessTokenCacheRepository,
                                                @Qualifier("profileAccessDatabaseRepository") ProfileAccessDatabaseRepository profileAccessDatabaseRepository) {
        return new AuthorizationService(accessTokenDatabaseRepository,
                accessTokenCacheRepository,
                profileAccessDatabaseRepository);
    }

}
