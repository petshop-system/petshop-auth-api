package com.petshop.auth.configuration.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfiguration {

    public static final String REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER = "redisCacheManagerBuilderCustomizer";

    public static final String AUTHENTICATION_CACHE_BASE_KEY = "#authentication_";

    public static final String AUTHENTICATION_CACHE_NAME = "authentication";

    public static final String ACCESS_TOKEN_CACHE_NAME = "access_token";

    public static final String ACCESS_TOKEN_CACHE_BASE_KEY = "#access_token_";

    @Bean
    @ConfigurationProperties("redis")
    RedisProperties redisProperties() {
        return new RedisProperties();
    }

    JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getHost(),
                redisProperties.getPort());
        config.setDatabase(redisProperties().getDatabase());
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisProperties redisProperties) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(this.jedisConnectionFactory(redisProperties));
        return template;
    }

    @Bean(RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER)
    public CacheManager redisCacheManagerBuilderCustomizer(RedisProperties redisProperties,
                                                           @Qualifier("objectMapper") ObjectMapper objectMapper) {

        JedisConnectionFactory jedisConnectionFactory = this.jedisConnectionFactory(redisProperties);
        jedisConnectionFactory.start();

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(jedisConnectionFactory)
                .withCacheConfiguration(AUTHENTICATION_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(redisProperties.getCache()
                                        .authentication().ttl()))
                                .serializeValuesWith(RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))))
                .withCacheConfiguration(ACCESS_TOKEN_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(redisProperties.getCache()
                                        .accessToken().ttl()))
                                .serializeValuesWith(RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))))
                .build();
    }

}


