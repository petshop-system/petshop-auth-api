package com.petshop.auth.utils.converter;

import com.petshop.auth.adapter.input.http.authentication.AuthenticationCodeValidationRequest;
import com.petshop.auth.adapter.input.http.authentication.AuthenticationCodeValidationResponse;
import com.petshop.auth.adapter.input.http.authentication.AuthenticationNewCodeValidationRequest;
import com.petshop.auth.adapter.input.http.authentication.AuthenticationRequest;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationCodeValidationProxyDomain;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationNewCodeValidationProxyDomain;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyDomain;
import com.petshop.auth.application.domain.AuthenticationCodeValidationDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.domain.AuthenticationNewCodeValidationDomain;
import org.mapstruct.Mapper;

@Mapper
public interface AuthenticationConverterMapper {

    AuthenticationProxyDomain toAuthenticationProxyDomain(AuthenticationDomain source);

    AuthenticationProxyDomain toAuthenticationProxyDomain(AuthenticationRequest source);

    AuthenticationNewCodeValidationProxyDomain toAuthenticationNewCodeValidationProxyDomain(AuthenticationNewCodeValidationRequest source);

    AuthenticationCodeValidationResponse toAuthenticationCodeValidationResponse(AuthenticationCodeValidationProxyDomain source);

    AuthenticationDomain toAuthenticationDomain(AuthenticationProxyDomain source);

    AuthenticationNewCodeValidationDomain toAuthenticationNewCodeValidationDomain(AuthenticationNewCodeValidationProxyDomain source);

    AuthenticationCodeValidationProxyDomain toAuthenticationCodeValidationProxyDomain(AuthenticationCodeValidationDomain source);

    AuthenticationCodeValidationProxyDomain toAuthenticationCodeValidationProxyDomain(AuthenticationCodeValidationRequest source);

    AuthenticationCodeValidationDomain toAuthenticationCodeValidationDomain(AuthenticationCodeValidationProxyDomain source);

}
