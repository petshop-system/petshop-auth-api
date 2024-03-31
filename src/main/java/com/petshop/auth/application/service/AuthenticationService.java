package com.petshop.auth.application.service;

import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.port.output.repository.AuthenticationCacheRepository;
import com.petshop.auth.application.port.output.repository.AuthenticationDatabaseRepository;
import com.petshop.auth.exception.UnauthorizedException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationService implements AuthenticationUsercase {

    private final AuthenticationDatabaseRepository authenticationDatabaseRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationDatabaseRepository authenticationDatabaseRepository,
                                 @Qualifier("BCryptPasswordEncoder") PasswordEncoder passwordEncoder) {
        this.authenticationDatabaseRepository = authenticationDatabaseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationDomain getByID(Long id) throws Exception {
        return null;
    }

    @Override
    public AuthenticationDomain create(AuthenticationDomain authenticationDomain) throws Exception {

        String passwordEncoded = passwordEncoder.encode(authenticationDomain.getPassword());
        authenticationDomain.setPassword(passwordEncoded);
        authenticationDomain = this.authenticationDatabaseRepository.save(authenticationDomain);
//        this.authenticationCacheRepository.set(authenticationDomain);

        return authenticationDomain;
    }

    @Override
    public AuthenticationDomain getByIdUser(Long idUsuario) throws Exception {
        return null;
    }

    @Override
    public AuthenticationDomain login(String login, String password) throws Exception {

        if (ObjectUtils.isEmpty(login) || ObjectUtils.isEmpty(password)) {
            throw new UnauthorizedException("login or password not informed");
        }

        AuthenticationDomain authenticationDomain = this.authenticationDatabaseRepository.getByLoginAndActive(login);
        if (ObjectUtils.isEmpty(authenticationDomain) ||
                !passwordEncoder.matches(password, authenticationDomain.getPassword())) {
            throw new UnauthorizedException("unauthorized access");
        }

        return authenticationDomain;
    }
}
