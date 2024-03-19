package com.petshop.auth.adapter.output.repository.cache.authentication;

import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRedisRepository extends CrudRepository<AuthenticationCacheDomain, String> {
}
