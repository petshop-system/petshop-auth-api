package com.petshop.auth.application.port.output.repository;

import com.petshop.auth.application.domain.SenhaDomain;

public interface SenhaCacheRepository {

    void set(SenhaDomain senhaDomain) throws Exception;
    SenhaDomain get(String key) throws Exception;
    void delete(String key) throws Exception;
}
