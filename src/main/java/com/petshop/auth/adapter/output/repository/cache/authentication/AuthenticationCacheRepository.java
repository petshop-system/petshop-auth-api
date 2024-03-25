package com.petshop.auth.adapter.output.repository.cache.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;

public class AuthenticationCacheRepository implements com.petshop.auth.application.port.output.repository.AuthenticationCacheRepository {

    private final AuthenticationRedisRepository authenticationRedisRepository;

    private final ObjectMapper objectMapper;

    private final AuthenticationConverterMapper authenticationConverterMapper;

    public AuthenticationCacheRepository(AuthenticationRedisRepository authenticationRedisRepository,
                                         ObjectMapper objectMapper,
                                         AuthenticationConverterMapper authenticationConverterMapper) {
        this.authenticationRedisRepository = authenticationRedisRepository;
        this.objectMapper = objectMapper;
        this.authenticationConverterMapper = authenticationConverterMapper;
    }

    @Override
    public void set(AuthenticationDomain authenticationDomain) throws Exception {
        AuthenticationCacheDomain authenticationCacheDomain =
                authenticationConverterMapper.converterToAuthenticationCacheDomain(authenticationDomain);
        authenticationRedisRepository.save(authenticationCacheDomain);
    }

    @Override
    public AuthenticationDomain get(String key) throws Exception {
        return null;
    }

    @Override
    public void delete(String key) throws Exception {

    }

}
