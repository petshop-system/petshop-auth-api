package com.petshop.auth.adapter.output.repository.database.accesstoken;

import org.springframework.data.repository.CrudRepository;

public interface AccessTokenJPARepository extends CrudRepository<AccessTokenDatabaseDomain, String> {
}
