package com.petshop.auth.application.port.output.repository;

import com.petshop.auth.application.domain.AccessTokenDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;

public interface AccessTokenCacheRepository {

    void set (AccessTokenDomain accessTokenDomain) throws Exception;

    AccessTokenDomain get (String token) throws Exception;

    void delete (String token) throws Exception;
}
