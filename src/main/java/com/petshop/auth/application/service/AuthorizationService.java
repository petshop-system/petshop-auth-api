package com.petshop.auth.application.service;

import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.AccessTokenDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.application.port.output.repository.AccessTokenCacheRepository;
import com.petshop.auth.application.port.output.repository.AccessTokenDatabaseRepository;
import com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository;
import com.petshop.auth.exception.ForbiddenException;
import com.petshop.auth.exception.UnauthorizedException;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthorizationService implements AuthorizationUserCase {

    private final String UNAUTHORIZED_TOKEN = "unauthorized token";

    private final String FORBIDDEN_ACCESS = "forbidden access";

    private final AccessTokenDatabaseRepository accessTokenDatabaseRepository;

    private final AccessTokenCacheRepository accessTokenCacheRepository;

    private final ProfileAccessDatabaseRepository profileAccessDatabaseRepository;

    public AuthorizationService(AccessTokenDatabaseRepository accessTokenDatabaseRepository,
                                AccessTokenCacheRepository accessTokenCacheRepository,
                                ProfileAccessDatabaseRepository profileAccessDatabaseRepository) {
        this.accessTokenDatabaseRepository = accessTokenDatabaseRepository;
        this.accessTokenCacheRepository = accessTokenCacheRepository;
        this.profileAccessDatabaseRepository = profileAccessDatabaseRepository;
    }

    @Override
    public void getNewAccessToken(String token, AuthenticationDomain authenticationDomain) throws Exception {

        AccessTokenDomain accessTokenDomain = accessTokenDatabaseRepository.save(authenticationDomain,
                token);
        accessTokenCacheRepository.set(accessTokenDomain);

    }

    @Override
    public void doAuthorization(String token, AccessDomain access) throws UnauthorizedException {

        try {

            AccessTokenDomain accessTokenDomain = accessTokenCacheRepository.get(token);
            if (ObjectUtils.isEmpty(accessTokenDomain))
                throw new UnauthorizedException(UNAUTHORIZED_TOKEN);

            // usar exemplo query para validar autorizacao
            // SELECT EXISTS(SELECT 1 FROM contact WHERE id=12)
            boolean isAuthorized = profileAccessDatabaseRepository.isAuthorized(accessTokenDomain.
                            getAuthenticationDomain().getProfile(),
                    access);

            if (!isAuthorized)
                throw new ForbiddenException(FORBIDDEN_ACCESS);

            accessTokenCacheRepository.set(accessTokenDomain);

        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }

    }

    @Override
    public void invalidateAccessToken(String token) throws Exception {
        accessTokenCacheRepository.delete(token);
    }

}
