package com.petshop.auth.adapter.output.repository.database.accesstoken;

import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationDatabaseDomain;
import com.petshop.auth.application.domain.AccessTokenDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "access_token", schema = "petshop_auth")
@Entity(name = "access_token")
public class AccessTokenDatabaseDomain implements Serializable {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "fk_id_authentication")
    private AuthenticationDatabaseDomain authenticationDatabaseDomain;

    AccessTokenDomain toAccessTokenDomain() {
        AccessTokenDomain accessTokenDomain = new AccessTokenDomain();
        accessTokenDomain.setToken(this.getToken());
        accessTokenDomain.setDateCreated(this.getDateCreated());
        accessTokenDomain.setAuthenticationDomain(this.getAuthenticationDatabaseDomain().toAuthenticationDomain());

        return accessTokenDomain;
    }

}
