package com.petshop.auth.adapter.output.repository.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.application.domain.SenhaDomain;

public class SenhaCacheRepository implements com.petshop.auth.application.port.output.repository.SenhaCacheRepository {

    private final SenhaRedisRepository senhaRedisRepository;

    private final ObjectMapper objectMapper;

    public SenhaCacheRepository(SenhaRedisRepository senhaRedisRepository, ObjectMapper objectMapper) {
        this.senhaRedisRepository = senhaRedisRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void set(SenhaDomain senhaDomain) throws Exception {
        SenhaCache senhaCache = new SenhaCache(senhaDomain);
        senhaRedisRepository.save(senhaCache);
    }

    @Override
    public SenhaDomain get(String key) throws Exception {
        return null;
    }

    @Override
    public void delete(String key) throws Exception {

    }
}
