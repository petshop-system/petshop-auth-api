package com.petshop.auth.adapter.output.repository.cache.accesstoken;

import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRedisRepository extends CrudRepository<AccessTokenRedisData, String> {
}
