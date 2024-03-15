package com.petshop.auth.application.domain;

import java.time.LocalDateTime;

public class AccessTokenDomain {

    private String token;

    private LocalDateTime dateCreated;

    private AuthenticationDomain authenticationDomain;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AuthenticationDomain getAuthenticationDomain() {
        return authenticationDomain;
    }

    public void setAuthenticationDomain(AuthenticationDomain authenticationDomain) {
        this.authenticationDomain = authenticationDomain;
    }
}
