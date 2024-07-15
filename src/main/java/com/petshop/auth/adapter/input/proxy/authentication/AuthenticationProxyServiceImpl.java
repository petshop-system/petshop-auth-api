package com.petshop.auth.adapter.input.proxy.authentication;

import com.petshop.auth.application.domain.AuthenticationCodeValidationDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.domain.AuthenticationNewCodeValidationDomain;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.configuration.redis.RedisConfiguration;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;
import org.springframework.cache.annotation.Cacheable;

public class AuthenticationProxyServiceImpl implements AuthenticationProxyService {

    private final AuthenticationUsercase authenticationUsercase;

    private final AuthenticationConverterMapper authenticationConverterMapper;

    public AuthenticationProxyServiceImpl(AuthenticationUsercase authenticationUsercase,
                                          AuthenticationConverterMapper authenticationConverterMapper) {
        this.authenticationUsercase = authenticationUsercase;
        this.authenticationConverterMapper = authenticationConverterMapper;
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
            key = "#newCodeValidationProxyDomain.id")
    @Override
    public AuthenticationCodeValidationProxyDomain newCodeValidation(AuthenticationNewCodeValidationProxyDomain newCodeValidationProxyDomain) throws Exception {
        // https://howtodoinjava.com/spring-boot/spring-boot-cache-example/
        AuthenticationNewCodeValidationDomain newCodeValidationDomain =
                authenticationConverterMapper.toAuthenticationNewCodeValidationDomain(newCodeValidationProxyDomain);

        AuthenticationCodeValidationDomain codeValidationDomain =
                authenticationUsercase.newCodeValidation(newCodeValidationDomain);

        AuthenticationCodeValidationProxyDomain code = authenticationConverterMapper.toAuthenticationCodeValidationProxyDomain(codeValidationDomain);
        return code;
    }

    @Cacheable(cacheManager = RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER,
            cacheNames = {"authentication_code_validation"},
            key = "#codeValidationDomain.reference")
    @Override
    public AuthenticationCodeValidationProxyDomain newCodeValidation(AuthenticationCodeValidationProxyDomain codeValidationDomain) throws Exception {
        return null;
    }

//    @Cacheable(cacheManager = RedisConfiguration.REDIS_CACHE_MANAGER_BUILDER_CUSTOMIZER,
//            cacheNames = {"authentication_code_validation"},
//            key = "#authentication_code_validation",
//            condition="#authentication_code_validation != null")
//    @Override
//    public AuthenticationCodeValidationProxyDomain getCodeValidation(AuthenticationCodeValidationProxyDomain codeValidationDomain) throws Exception {
//
//    }


}
