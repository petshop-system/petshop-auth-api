package com.petshop.auth.application.port.output.repository;

import com.petshop.auth.application.domain.AccessTokenDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;

public interface AccessTokenDatabaseRepository {

    AccessTokenDomain save (AuthenticationDomain authenticationDomain, String token) throws Exception;



}
