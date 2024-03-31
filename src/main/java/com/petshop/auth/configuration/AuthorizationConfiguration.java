package com.petshop.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.application.port.output.repository.AccessTokenCacheRepository;
import com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository;
import com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository;
import com.petshop.auth.application.service.AuthorizationService;
import com.petshop.auth.configuration.redis.RedisConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationConfiguration {

    @Bean("authorizationUserCase")
    AuthorizationUserCase authorizationUserCase(@Qualifier("accessTokenDatabaseRepository") AccessTokenDatabaseRepository accessTokenDatabaseRepository,
                                                @Qualifier("profileAccessDatabaseRepository") ProfileAccessDatabaseRepository profileAccessDatabaseRepository,
                                                @Qualifier(RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER) CacheManager redisCacheManagerBuilderCustomizer,
                                                @Qualifier("objectMapper") ObjectMapper objectMapper) {
        return new AuthorizationService(accessTokenDatabaseRepository,
                profileAccessDatabaseRepository, redisCacheManagerBuilderCustomizer,
                objectMapper);
    }

}
