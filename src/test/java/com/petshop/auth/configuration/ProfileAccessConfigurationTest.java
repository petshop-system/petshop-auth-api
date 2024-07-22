package com.petshop.auth.configuration;

import com.petshop.auth.adapter.output.repository.database.profileaccess.ProfileAccessJPARepository;
import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.ProfileDomain;
import com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
public class ProfileAccessConfigurationTest {

    @Bean(ProfileAccessConfiguration.PROFILE_ACCESS_DATABASE_REPOSITORY)
    public ProfileAccessDatabaseRepository profileAccessDatabaseRepository() {
        return new ProfileAccessDatabaseRepository() {
            @Override
            public boolean isAuthorized(ProfileDomain.Profile profile, AccessDomain access) {
                return false;
            }

            @Override
            public void save(ProfileDomain.Profile profile, AccessDomain access) {

            }
        };
    }

}
