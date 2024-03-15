package com.petshop.auth.adapter.output.repository.database.authentication;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationJPARepository extends JpaRepository<AuthenticationDatabaseDomain, Long> {

    AuthenticationDatabaseDomain getByLoginAndActive(String login, Boolean active);

}
