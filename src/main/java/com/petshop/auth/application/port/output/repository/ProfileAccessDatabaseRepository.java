package com.petshop.auth.application.port.output.repository;

import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.ProfileDomain;

public interface ProfileAccessDatabaseRepository {

    boolean isAuthorized(ProfileDomain.Profile profile, AccessDomain access);

    void save (ProfileDomain.Profile profile, AccessDomain access);

}
