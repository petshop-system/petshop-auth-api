package com.petshop.auth.application.port.output.repository;

import com.petshop.auth.application.domain.AuthenticationDomain;

public interface AuthenticationCacheRepository {

    void set(AuthenticationDomain authenticationDomain) throws Exception;
    AuthenticationDomain get(String key) throws Exception;
    void delete(String key) throws Exception;
}
