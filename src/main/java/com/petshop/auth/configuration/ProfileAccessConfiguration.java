package com.petshop.auth.configuration;

import com.petshop.auth.adapter.output.repository.database.profileaccess.ProfileAccessJPARepository;
import com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileAccessConfiguration {

    public static final String PROFILE_ACCESS_DATABASE_REPOSITORY = "profileAccessDatabaseRepository";

    @Bean(PROFILE_ACCESS_DATABASE_REPOSITORY)
    @Profile("!test")
    public ProfileAccessDatabaseRepository profileAccessDatabaseRepository(ProfileAccessJPARepository profileAccessJPARepository) {
        return new com.petshop.auth.adapter.output.repository.database.profileaccess
                .ProfileAccessDatabaseRepository(profileAccessJPARepository);
    }

}
