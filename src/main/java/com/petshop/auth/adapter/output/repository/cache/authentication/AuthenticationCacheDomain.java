package com.petshop.auth.adapter.output.repository.cache.authentication;

import com.petshop.auth.application.domain.AuthenticationDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Data
@RedisHash(timeToLive = 300, value = "Authentication")
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationCacheDomain implements Serializable {

    @NonNull
    private Long id;

    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    private Long idUser;

    private Boolean active;

    private String profile;

//    public AuthenticationCacheDomain(AuthenticationDomain authenticationDomain) {
//        this.setPassword(authenticationDomain.getPassword());
//        this.setId(authenticationDomain.getId());
//        this.setLogin(authenticationDomain.getLogin());
//        this.setIdUser(authenticationDomain.getIdUser());
//        this.setActive(authenticationDomain.getActive());
//        this.setProfile(authenticationDomain.getProfile().getName());
//    }
}
