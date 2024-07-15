package com.petshop.auth.adapter.input.proxy.authentication;

import com.petshop.auth.application.domain.AuthenticationCodeValidationDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.domain.AuthenticationNewCodeValidationDomain;

public interface AuthenticationProxyService {

    AuthenticationProxyDomain create(AuthenticationProxyDomain authenticationProxyDomain) throws Exception;

    AuthenticationProxyDomain getByIdUser(Long idUser) throws Exception;

    AuthenticationProxyDomain login(String login, String password) throws Exception;

    AuthenticationCodeValidationProxyDomain newCodeValidation(AuthenticationNewCodeValidationProxyDomain newCodeValidationDomain) throws Exception;

    AuthenticationCodeValidationProxyDomain newCodeValidation(AuthenticationCodeValidationProxyDomain codeValidationDomain) throws Exception;

//    AuthenticationCodeValidationProxyDomain getCodeValidation (AuthenticationCodeValidationProxyDomain codeValidationDomain) throws Exception;
//
//    AuthenticationCodeValidationProxyDomain validateCodeValidation (String reference) throws Exception;

}
