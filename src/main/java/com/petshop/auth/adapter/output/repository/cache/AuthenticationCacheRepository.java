package com.petshop.auth.adapter.output.repository.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.application.domain.AuthenticationDomain;

public class AuthenticationCacheRepository implements com.petshop.auth.application.port.output.repository.AuthenticationCacheRepository {

    private final AuthenticationRedisRepository authenticationRedisRepository;

    private final ObjectMapper objectMapper;

    public AuthenticationCacheRepository(AuthenticationRedisRepository authenticationRedisRepository, ObjectMapper objectMapper) {
        this.authenticationRedisRepository = authenticationRedisRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void set(AuthenticationDomain authenticationDomain) throws Exception {
        AuthenticationCache authenticationCache = new AuthenticationCache(authenticationDomain);
        authenticationRedisRepository.save(authenticationCache);
    }

    @Override
    public AuthenticationDomain get(String key) throws Exception {
        return null;
    }

    @Override
    public void delete(String key) throws Exception {

    }

}
