package com.petshop.auth.adapter.input.proxy.authentication;

import com.petshop.auth.application.domain.ProfileDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationProxyDomain {

    private Long id;

    private String login;

    private String password;

    private Long idUser;

    private ProfileDomain.Profile profile;

    private Boolean active;
}
