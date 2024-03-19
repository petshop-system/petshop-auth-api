package com.petshop.auth.adapter.output.repository.database.authentication;

import com.petshop.auth.application.domain.AuthenticationDomain;
import org.apache.commons.lang3.ObjectUtils;

public class AuthenticationDatabaseRepository implements com.petshop.auth.application.port.output.repository.AuthenticationDatabaseRepository {

    private final AuthenticationJPARepository authenticationJPARepository;

    public AuthenticationDatabaseRepository(AuthenticationJPARepository authenticationJPARepository) {
        this.authenticationJPARepository = authenticationJPARepository;
    }

    @Override
    public AuthenticationDomain save(AuthenticationDomain authenticationDomain) throws Exception {

        AuthenticationDatabaseDomain authenticationDatabaseDomain = new AuthenticationDatabaseDomain(authenticationDomain);
        AuthenticationDatabaseDomain save = this.authenticationJPARepository.save(authenticationDatabaseDomain);

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
    public AuthenticationDomain getByLoginAndActive(String login) throws Exception {

        AuthenticationDatabaseDomain authenticationDatabaseDomain =
                authenticationJPARepository.getByLoginAndActive(login, true);

        return ObjectUtils.isEmpty(authenticationDatabaseDomain)?
                null: authenticationDatabaseDomain.toAuthenticationDomain();
    }
}
