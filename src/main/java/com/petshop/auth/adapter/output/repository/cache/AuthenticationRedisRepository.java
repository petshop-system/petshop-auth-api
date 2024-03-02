package com.petshop.auth.adapter.output.repository.cache;

import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRedisRepository extends CrudRepository<AuthenticationCache, String> {
}
