package com.petshop.auth.configuration;

import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.output.repository.AuthenticationDatabaseRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
public class AuthenticationConfigurationTest {

    @Bean(AuthenticationConfiguration.AUTHENTICATION_DATABASE_REPOSITORY)
    public AuthenticationDatabaseRepository authenticationDatabaseRepository () {
        return new AuthenticationDatabaseRepository() {
            @Override
            public AuthenticationDomain save(AuthenticationDomain authenticationDomain) throws Exception {
                return null;
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
                return null;
            }
        };
    }
}
