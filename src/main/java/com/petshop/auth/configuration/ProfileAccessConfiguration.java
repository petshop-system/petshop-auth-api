package com.petshop.auth.configuration;

import com.petshop.auth.adapter.output.repository.database.profileaccess.ProfileAccessJPARepository;
import com.petshop.auth.application.port.output.repository.ProfileAccessDatabaseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileAccessConfiguration {

    @Bean("profileAccessDatabaseRepository")
    public ProfileAccessDatabaseRepository profileAccessDatabaseRepository(ProfileAccessJPARepository profileAccessJPARepository) {
        return new com.petshop.auth.adapter.output.repository.database.profileaccess
                .ProfileAccessDatabaseRepository(profileAccessJPARepository);
    }

}
