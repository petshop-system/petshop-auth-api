package com.petshop.auth.adapter.output.repository.database.profileaccess;

import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.ProfileDomain;

public class ProfileAccessDatabaseRepository implements com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository {

    private final ProfileAccessJPARepository profileAccessJPARepository;

    public ProfileAccessDatabaseRepository(ProfileAccessJPARepository profileAccessJPARepository) {
        this.profileAccessJPARepository = profileAccessJPARepository;
    }

    @Override
    public boolean isAuthorized(ProfileDomain.Profile profile, AccessDomain access) {
        return profileAccessJPARepository.existsProfileForAccess(profile.getName(), access.getAction());
    }

    @Override
    public void save(ProfileDomain.Profile profile, AccessDomain access) {

    }
}
