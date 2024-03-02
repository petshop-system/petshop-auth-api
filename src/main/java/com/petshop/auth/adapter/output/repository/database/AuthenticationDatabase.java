package com.petshop.auth.adapter.output.repository.database;

import com.petshop.auth.application.domain.AuthenticationDomain;
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
public class AuthenticationDatabase implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String login;

    private String password;

    @Column(name = "id_user")
    private Long idUser;

    public AuthenticationDatabase(AuthenticationDomain authenticationDomain) {
        this.setPassword(authenticationDomain.getPassword());
        this.setId(authenticationDomain.getId());
        this.setLogin(authenticationDomain.getLogin());
        this.setIdUser(authenticationDomain.getIdUser());
    }

    AuthenticationDomain toAuthenticationDomain() {
        AuthenticationDomain authenticationDomain = new AuthenticationDomain();
        authenticationDomain.setPassword(this.getPassword());
        authenticationDomain.setId(this.getId());
        authenticationDomain.setLogin(this.getLogin());
        authenticationDomain.setIdUser(this.getIdUser());

        return authenticationDomain;
    }

}
