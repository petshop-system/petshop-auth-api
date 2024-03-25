package com.petshop.auth.utils.converter;

import com.petshop.auth.adapter.output.repository.cache.authentication.AuthenticationCacheDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import org.mapstruct.Mapper;

@Mapper
public interface AuthenticationConverterMapper {

    AuthenticationCacheDomain converterToAuthenticationCacheDomain(AuthenticationDomain source);
}
