package com.petshop.auth.adapter.output.repository.database.accesstoken;

import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationDatabaseDomain;
import com.petshop.auth.application.domain.AccessTokenDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;

import java.time.LocalDateTime;

public class AccessTokenDatabaseRepository implements com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository {

    private final AccessTokenJPARepository accessTokenJPARepository;

    public AccessTokenDatabaseRepository(AccessTokenJPARepository accessTokenJPARepository) {
        this.accessTokenJPARepository = accessTokenJPARepository;
    }

    @Override
    public AccessTokenDomain save(AuthenticationDomain authenticationDomain, String token) throws Exception {
        AccessTokenDatabaseDomain accessTokenDatabaseDomain =
                new AccessTokenDatabaseDomain(token, LocalDateTime.now(), new AuthenticationDatabaseDomain(authenticationDomain));

        return accessTokenJPARepository.save(accessTokenDatabaseDomain).toAccessTokenDomain();
    }
}
