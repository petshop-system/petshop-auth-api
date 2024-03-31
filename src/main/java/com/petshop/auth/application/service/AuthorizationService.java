package com.petshop.auth.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.AccessTokenDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.application.port.output.repository.AccessTokenCacheRepository;
import com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository;
import com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository;
import com.petshop.auth.configuration.redis.RedisConfiguration;
import com.petshop.auth.exception.ForbiddenException;
import com.petshop.auth.exception.UnauthorizedException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class AuthorizationService implements AuthorizationUserCase {

    private final String UNAUTHORIZED_TOKEN = "unauthorized token";

    private final String FORBIDDEN_ACCESS = "forbidden access";

    private final AccessTokenDatabaseRepository accessTokenDatabaseRepository;

    private final ProfileAccessDatabaseRepository profileAccessDatabaseRepository;

    private final Cache accessTokenCacheRepository;

    private final ObjectMapper objectMapper;

    public AuthorizationService(AccessTokenDatabaseRepository accessTokenDatabaseRepository,
                                ProfileAccessDatabaseRepository profileAccessDatabaseRepository,
                                CacheManager redisCacheManagerBuilderCustomizer,
                                ObjectMapper objectMapper) {
        this.accessTokenDatabaseRepository = accessTokenDatabaseRepository;
        this.profileAccessDatabaseRepository = profileAccessDatabaseRepository;
        this.accessTokenCacheRepository = redisCacheManagerBuilderCustomizer
                .getCache(RedisConfiguration.ACCESS_TOKEN_CACHE_NAME);
        this.objectMapper = objectMapper;
    }

    @Override
    public void getNewAccessToken(String token, AuthenticationDomain authenticationDomain) throws Exception {

        AccessTokenDomain accessTokenDomain = accessTokenDatabaseRepository.save(authenticationDomain, token);
        accessTokenCacheRepository.put(token, accessTokenDomain);
    }

    @Override
    public void doAuthorization(String token, AccessDomain access) throws UnauthorizedException {

        try {

            Cache.ValueWrapper valueWrapper = accessTokenCacheRepository.get(token);
            AccessTokenDomain accessTokenDomain = null;
            if (ObjectUtils.isNotEmpty(valueWrapper) && ObjectUtils.isNotEmpty(valueWrapper.get())) {
                accessTokenDomain = objectMapper
                        .readValue(objectMapper
                                .writeValueAsString(valueWrapper.get()), AccessTokenDomain.class);
            }

            if (ObjectUtils.isEmpty(accessTokenDomain))
                throw new UnauthorizedException(UNAUTHORIZED_TOKEN);

            // usar exemplo query para validar autorizacao
            // SELECT EXISTS(SELECT 1 FROM contact WHERE id=12)
            boolean isAuthorized = profileAccessDatabaseRepository.isAuthorized(accessTokenDomain.
                            getAuthenticationDomain().getProfile(), access);

            if (!isAuthorized)
                throw new ForbiddenException(FORBIDDEN_ACCESS);

            accessTokenCacheRepository.put(token, accessTokenDomain);

        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }

    }

    @Override
    public void invalidateAccessToken(String token) throws Exception {
        accessTokenCacheRepository.evict(token);
    }

}
