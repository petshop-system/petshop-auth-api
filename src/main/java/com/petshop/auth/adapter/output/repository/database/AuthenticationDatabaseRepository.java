package com.petshop.auth.adapter.output.repository.database;

import com.petshop.auth.application.domain.AuthenticationDomain;
import org.apache.commons.lang3.ObjectUtils;

public class AuthenticationDatabaseRepository implements com.petshop.auth.application.port.output.repository.AuthenticationDatabaseRepository {

    private final AuthenticationJPARepository authenticationJPARepository;

    public AuthenticationDatabaseRepository(AuthenticationJPARepository authenticationJPARepository) {
        this.authenticationJPARepository = authenticationJPARepository;
    }

    @Override
    public AuthenticationDomain Save(AuthenticationDomain authenticationDomain) throws Exception {

        AuthenticationDatabase authenticationDatabase = new AuthenticationDatabase(authenticationDomain);
        AuthenticationDatabase save = this.authenticationJPARepository.save(authenticationDatabase);

        return save.toAuthenticationDomain();
    }

    @Override
    public AuthenticationDomain getByID(Long id) throws Exception {
        return null;
    }

    @Override
    public AuthenticationDomain getByIdUser(Long idUser) throws Exception {
        return null;
    }

    @Override
    public AuthenticationDomain getByLoginAndPassword(String login, String password) throws Exception {

        AuthenticationDatabase authenticationDatabase =
                authenticationJPARepository.getByLoginAndPassword(login, password);

        return ObjectUtils.isEmpty(authenticationDatabase)?
                null:authenticationDatabase.toAuthenticationDomain();
    }
}
