package com.petshop.auth.adapter.output.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationJPARepository extends JpaRepository<AuthenticationDatabase, Long> {

    AuthenticationDatabase getByLogin(String login);

}