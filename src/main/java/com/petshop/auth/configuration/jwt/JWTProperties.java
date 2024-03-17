package com.petshop.auth.configuration.jwt;

public class JWTProperties {


    private String jwtCookieName;

    private String jwtSecret;

    private Integer jwtExpirationMS;

    public String getJwtCookieName() {
        return jwtCookieName;
    }

    public void setJwtCookieName(String jwtCookieName) {
        this.jwtCookieName = jwtCookieName;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public Integer getJwtExpirationMS() {
        return jwtExpirationMS;
    }

    public void setJwtExpirationMS(Integer jwtExpirationMS) {
        this.jwtExpirationMS = jwtExpirationMS;
    }
}
