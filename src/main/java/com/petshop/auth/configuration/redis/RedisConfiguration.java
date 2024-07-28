package com.petshop.auth.configuration.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.ConnectionFactory;
import redis.clients.jedis.HostAndPort;

import java.time.Duration;

@Configuration
@Profile("!test")
@EnableCaching
public class RedisConfiguration {

    public static final String REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER = "redisCacheManagerBuilderCustomizer";

    public static final String AUTHENTICATION_CACHE_BASE_KEY = "#authentication_";

    public static final String AUTHENTICATION_CACHE_NAME = "authentication";

    public static final String ACCESS_TOKEN_CACHE_NAME = "access_token";

    public static final String ACCESS_TOKEN_CACHE_BASE_KEY = "#access_token_";

    public static final String AUTHENTICATION_CODE_VALIDATION_CACHE_NAME = "authentication_code_validation";
    public static final String AUTHENTICATION_CODE_VALIDATION_CACHE_BASE_KEY = "#authentication_code_validation_";

    @Bean
    @ConfigurationProperties("redis")
    RedisProperties redisProperties() {
        return new RedisProperties();
    }

    @Bean("jedisConnectionFactory")
    JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getHost(),
                redisProperties.getPort());
        config.setDatabase(redisProperties().getDatabase());

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config);
        jedisConnectionFactory.start();
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisProperties redisProperties) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(this.jedisConnectionFactory(redisProperties));
        return template;
    }

    @Bean(RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER)
    public CacheManager redisCacheManagerBuilderCustomizer(RedisProperties redisProperties,
                                                           @Qualifier("objectMapper") ObjectMapper objectMapper,
                                                           @Qualifier("jedisConnectionFactory") RedisConnectionFactory connectionFactory) {

        var authenticationCache = this.getRedisCacheConfiguration(redisProperties.getCache()
                .authentication().ttl(), objectMapper);

        var accessTokenCache = this.getRedisCacheConfiguration(redisProperties.getCache()
                .accessToken().ttl(), objectMapper);

        var authenticationCodeValidation = this.getRedisCacheConfiguration(redisProperties.getCache()
                        .authenticationCodeValidation().ttl(), objectMapper);

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .withCacheConfiguration(AUTHENTICATION_CACHE_NAME, authenticationCache)
                .withCacheConfiguration(ACCESS_TOKEN_CACHE_NAME, accessTokenCache)
                .withCacheConfiguration(AUTHENTICATION_CODE_VALIDATION_CACHE_NAME, authenticationCodeValidation)
                .build();
    }

    private RedisCacheConfiguration getRedisCacheConfiguration(int ttl, ObjectMapper objectMapper) {

        return RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                .entryTtl(Duration.ofSeconds(ttl))
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
    }

}


