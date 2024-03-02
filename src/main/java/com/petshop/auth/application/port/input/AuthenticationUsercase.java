package com.petshop.auth.application.port.input;

import com.petshop.auth.application.domain.AuthenticationDomain;

public interface AuthenticationUsercase {

    AuthenticationDomain getByID(Long id) throws Exception;;

    AuthenticationDomain create(AuthenticationDomain authenticationDomain) throws Exception;;

    AuthenticationDomain getByIdUser(Long idUser) throws Exception;;

    AuthenticationDomain getByLoginAndPassword(String login, String password) throws Exception;;

}
