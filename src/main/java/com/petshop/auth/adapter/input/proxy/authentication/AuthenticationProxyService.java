package com.petshop.auth.adapter.input.proxy.authentication;

import com.petshop.auth.application.domain.AuthenticationDomain;

public interface AuthenticationProxyService {

    AuthenticationProxyDomain create(AuthenticationProxyDomain authenticationProxyDomain) throws Exception;

    AuthenticationProxyDomain getByIdUser(Long idUser) throws Exception;

    AuthenticationProxyDomain login(String login, String password) throws Exception;

}
