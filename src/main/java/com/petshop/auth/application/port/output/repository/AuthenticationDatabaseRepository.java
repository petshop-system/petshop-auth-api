package com.petshop.auth.application.port.output.repository;

import com.petshop.auth.application.domain.AuthenticationDomain;

public interface AuthenticationDatabaseRepository {

    AuthenticationDomain save (AuthenticationDomain authenticationDomain) throws Exception;

    AuthenticationDomain getByID(Long id) throws Exception;;

    AuthenticationDomain getByIdUser(Long idUser) throws Exception;

    AuthenticationDomain getByLoginAndActive(String login) throws Exception;

}
