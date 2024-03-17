package com.petshop.auth.adapter.output.repository.cache.accesstoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationDatabaseDomain;
import com.petshop.auth.application.domain.AccessTokenDomain;

import java.time.LocalDateTime;
import java.util.Optional;

public class AccessTokenCacheRepository implements com.petshop.auth.application.port.output.repository.AccessTokenCacheRepository {

    private final AccessTokenRedisRepository accessTokenRedisRepository;

    private final ObjectMapper objectMapper;

    public AccessTokenCacheRepository(AccessTokenRedisRepository accessTokenRedisRepository, ObjectMapper objectMapper) {
        this.accessTokenRedisRepository = accessTokenRedisRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void set(AccessTokenDomain accessTokenDomain) throws Exception {

        String json = objectMapper.writeValueAsString(new AccessTokenCacheDomain(accessTokenDomain.getToken(),
                LocalDateTime.now(),
                new AuthenticationDatabaseDomain(accessTokenDomain.getAuthenticationDomain())));

        accessTokenRedisRepository.save(new AccessTokenRedisData(accessTokenDomain.getToken(), json));
    }

    @Override
    public AccessTokenDomain get(String token) throws Exception {

        Optional<AccessTokenRedisData> value = accessTokenRedisRepository.findById(token);
        if (value.isPresent()) {
            AccessTokenCacheDomain accessTokenCacheDomain =
                    objectMapper.readValue(value.get().getValue(), AccessTokenCacheDomain.class);
            return accessTokenCacheDomain.toAccessTokenDomain();
        }
        return null;
    }

    @Override
    public void delete(String token) throws Exception {
        accessTokenRedisRepository.deleteById(token);
    }
}
