package com.petshop.auth.adapter.input.proxy.authentication;

import com.petshop.auth.application.domain.AuthenticationCodeValidationDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.domain.AuthenticationNewCodeValidationDomain;
import com.petshop.auth.exception.ForbiddenException;

public interface AuthenticationProxyService {

    AuthenticationProxyDomain create(AuthenticationProxyDomain authenticationProxyDomain) throws Exception;

    AuthenticationProxyDomain getByIdUser(Long idUser) throws Exception;

    AuthenticationProxyDomain login(String login, String password) throws Exception;

    String getCodeValidation(String reference, int digits) throws Exception;

    Void validateCodeValidation(String referenceRequest, String referenceStored,
                                String codeRequest, String codeStored) throws Exception;

}
