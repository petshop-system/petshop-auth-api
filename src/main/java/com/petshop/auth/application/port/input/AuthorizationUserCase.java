package com.petshop.auth.application.port.input;

import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.exception.UnauthorizedException;

public interface AuthorizationUserCase {

    void getNewAccessToken(String token, AuthenticationDomain authenticationDomain) throws Exception;

    void doAuthorization(String token, AccessDomain access) throws UnauthorizedException;

    void invalidateAccessToken(String token) throws Exception;

}
