package com.petshop.auth.application.service;

import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.port.output.repository.AuthenticationCacheRepository;
import com.petshop.auth.application.port.output.repository.AuthenticationDatabaseRepository;
import com.petshop.auth.exception.InternalServerErrorException;
import org.apache.commons.lang3.ObjectUtils;

public class AuthenticationService implements AuthenticationUsercase {

    private final AuthenticationCacheRepository authenticationCacheRepository;

    private final AuthenticationDatabaseRepository authenticationDatabaseRepository;

    public AuthenticationService(AuthenticationCacheRepository authenticationCacheRepository, AuthenticationDatabaseRepository authenticationDatabaseRepository) {
        this.authenticationCacheRepository = authenticationCacheRepository;
        this.authenticationDatabaseRepository = authenticationDatabaseRepository;
    }

    @Override
    public AuthenticationDomain getByID(Long id) throws Exception {
        return null;
    }

    @Override
    public AuthenticationDomain create(AuthenticationDomain authenticationDomain) throws Exception {

        authenticationDomain = this.authenticationDatabaseRepository.Save(authenticationDomain);
        this.authenticationCacheRepository.set(authenticationDomain);

        return authenticationDomain;
    }

    @Override
    public AuthenticationDomain getByIdUser(Long idUsuario) throws Exception {
        return null;
    }

    @Override
    public AuthenticationDomain getByLoginAndPassword(String login, String password) throws Exception {

        if (ObjectUtils.isEmpty(login) || ObjectUtils.isEmpty(password)) {
            throw new InternalServerErrorException("login or password not informed");
        }

        return this.authenticationDatabaseRepository.getByLoginAndPassword(login, password);
    }
}
