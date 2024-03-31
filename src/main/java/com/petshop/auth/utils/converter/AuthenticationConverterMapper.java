package com.petshop.auth.utils.converter;

import com.petshop.auth.adapter.input.http.authentication.AuthenticationRequest;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import org.mapstruct.Mapper;

@Mapper
public interface AuthenticationConverterMapper {

    AuthenticationProxyDomain toAuthenticationProxyDomain(AuthenticationDomain source);

    AuthenticationProxyDomain toAuthenticationProxyDomain(AuthenticationRequest source);

    AuthenticationDomain toAuthenticationDomain(AuthenticationProxyDomain source);

}
