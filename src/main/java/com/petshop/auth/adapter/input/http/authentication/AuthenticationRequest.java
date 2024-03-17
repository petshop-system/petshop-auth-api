package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.domain.ProfileDomain;

public record AuthenticationRequest(@JsonProperty("login") String login,
                                    @JsonProperty("id") Long id,
                                    @JsonProperty("password") String password,
                                    @JsonProperty("id_user") Long idUser,

                                    @JsonProperty("profile") ProfileDomain.Profile profile,

                                    @JsonProperty("active") Boolean active
                                    ) {

    AuthenticationDomain toAuthenticationDomain() {
        AuthenticationDomain authenticationDomain = new AuthenticationDomain();
        authenticationDomain.setLogin(this.login());
        authenticationDomain.setId(this.id());
        authenticationDomain.setPassword(this.password());
        authenticationDomain.setIdUser(this.idUser());
        authenticationDomain.setProfile(this.profile());
        authenticationDomain.setActive(this.active());

        return authenticationDomain;
    }
}
