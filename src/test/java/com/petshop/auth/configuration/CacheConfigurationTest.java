package com.petshop.auth.configuration;


import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.configuration.redis.RedisConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Collection;

@EnableCaching
@TestConfiguration
@Profile("test")
public class CacheConfigurationTest {
    @Bean(RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER)
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

}
