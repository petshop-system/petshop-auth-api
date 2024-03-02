package com.petshop.auth.adapter.input.http.senha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.petshop.auth.application.domain.AuthenticationDomain;

public record AuthenticationRequest(@JsonProperty("login") String login,
                                    @JsonProperty("id") Long id,
                                    @JsonProperty("password") String password,
                                    @JsonProperty("id_user") Long idUser) {

    AuthenticationDomain toAuthenticationDomain() {
        AuthenticationDomain authenticationDomain = new AuthenticationDomain();
        authenticationDomain.setLogin(this.login());
        authenticationDomain.setId(this.id());
        authenticationDomain.setPassword(this.password());
        authenticationDomain.setIdUser(this.idUser());

        return authenticationDomain;
    }
}
