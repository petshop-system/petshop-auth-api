package com.petshop.auth.adapter.output.repository.database.authentication;

import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.domain.ProfileDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authentication", schema = "petshop_auth")
@Entity(name = "authentication")
public class AuthenticationDatabaseDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String login;

    private String password;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "fk_profile")
    private String profile;

    public AuthenticationDatabaseDomain(AuthenticationDomain authenticationDomain) {
        this.setPassword(authenticationDomain.getPassword());
        this.setId(authenticationDomain.getId());
        this.setLogin(authenticationDomain.getLogin());
        this.setIdUser(authenticationDomain.getIdUser());
        this.setProfile(authenticationDomain.getProfile().getName());
        this.setActive(authenticationDomain.getActive());
    }

    public AuthenticationDomain toAuthenticationDomain() {
        AuthenticationDomain authenticationDomain = new AuthenticationDomain();
        authenticationDomain.setPassword(this.getPassword());
        authenticationDomain.setId(this.getId());
        authenticationDomain.setLogin(this.getLogin());
        authenticationDomain.setIdUser(this.getIdUser());
        authenticationDomain.setProfile(ProfileDomain.Profile.valueOf(this.getProfile()));

        return authenticationDomain;
    }

}
