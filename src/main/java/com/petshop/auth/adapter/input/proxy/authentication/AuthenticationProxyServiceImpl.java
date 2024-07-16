package com.petshop.auth.adapter.input.proxy.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.application.domain.AuthenticationCodeValidationDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.domain.AuthenticationNewCodeValidationDomain;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.configuration.redis.RedisConfiguration;
import com.petshop.auth.exception.ForbiddenException;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;

public class AuthenticationProxyServiceImpl implements AuthenticationProxyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationUsercase authenticationUsercase;

    private final AuthenticationConverterMapper authenticationConverterMapper;

    private final ObjectMapper objectMapper;

    public AuthenticationProxyServiceImpl(AuthenticationUsercase authenticationUsercase,
                                          AuthenticationConverterMapper authenticationConverterMapper,
                                          @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.authenticationUsercase = authenticationUsercase;
        this.authenticationConverterMapper = authenticationConverterMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public AuthenticationProxyDomain create(AuthenticationProxyDomain authenticationProxyDomain) throws Exception {
        AuthenticationDomain ad = authenticationConverterMapper
                .toAuthenticationDomain(authenticationProxyDomain);
        ad = authenticationUsercase.create(ad);
        return authenticationConverterMapper.toAuthenticationProxyDomain(ad);
    }

    @Override
    public AuthenticationProxyDomain getByIdUser(Long idUser) throws Exception {
        return authenticationConverterMapper
                .toAuthenticationProxyDomain(authenticationUsercase.getByIdUser(idUser));
    }

    @Cacheable(cacheManager = RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER,
            cacheNames = {"authentication"},
            key = "#login")
    @Override
    public AuthenticationProxyDomain login(String login, String password) throws Exception {
        AuthenticationDomain ad = authenticationUsercase.login(login, password);
        return authenticationConverterMapper
                .toAuthenticationProxyDomain(ad);
    }

    @Cacheable(cacheManager = RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER,
            cacheNames = {"authentication_code_validation"},
            key = "#reference")
    @Override
    public String getCodeValidation(String reference, int digits) throws Exception {
        // https://howtodoinjava.com/spring-boot/spring-boot-cache-example/

        AuthenticationNewCodeValidationDomain newCodeValidationDomain =
                new AuthenticationNewCodeValidationDomain(reference, digits);

        AuthenticationCodeValidationDomain codeValidationDomain =
                authenticationUsercase.newCodeValidation(newCodeValidationDomain);

        return objectMapper.writeValueAsString(codeValidationDomain);
    }

    @Override
    public void validateCodeValidation(String referenceRequest, String referenceStored,
                                       String codeRequest, String codeStored) throws Exception {

        authenticationUsercase.validateCodeValidation(referenceRequest, referenceStored,
                codeRequest, codeStored);

    }

}
