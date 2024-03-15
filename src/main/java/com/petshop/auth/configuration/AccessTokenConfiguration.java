package com.petshop.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.output.repository.cache.accesstoken.AccessTokenRedisRepository;
import com.petshop.auth.adapter.output.repository.database.accesstoken.AccessTokenJPARepository;
import com.petshop.auth.application.port.output.repository.AccessTokenCacheRepository;
import com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenConfiguration {

    @Bean("accessTokenDatabaseRepository")
    public AccessTokenDatabaseRepository accessTokenDatabaseRepository(AccessTokenJPARepository accessTokenJPARepository) {
        return new com.petshop.auth.adapter.output.repository.database.accesstoken
                .AccessTokenDatabaseRepository(accessTokenJPARepository);
    }

    @Bean("accessTokenCacheRepository")
    public AccessTokenCacheRepository accessTokenCacheRepository(AccessTokenRedisRepository accessTokenRedisRepository,
                                                                 ObjectMapper objectMapper){
        return new com.petshop.auth.adapter.output.repository.cache.accesstoken
                .AccessTokenCacheRepository(accessTokenRedisRepository, objectMapper);
    }

}
