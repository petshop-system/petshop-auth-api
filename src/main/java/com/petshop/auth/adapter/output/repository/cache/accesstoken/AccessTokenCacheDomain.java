package com.petshop.auth.adapter.output.repository.cache.accesstoken;

import com.petshop.auth.adapter.output.repository.database.authentication.AuthenticationDatabaseDomain;
import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.AccessTokenDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenCacheDomain implements Serializable {

    private String token;

    private LocalDateTime dateCreated;

    private AuthenticationDatabaseDomain authenticationDatabaseDomain;

    AccessTokenDomain toAccessTokenDomain() {
        AccessTokenDomain accessTokenDomain = new AccessTokenDomain();
        accessTokenDomain.setAuthenticationDomain(this.getAuthenticationDatabaseDomain().toAuthenticationDomain());
        accessTokenDomain.setDateCreated(this.getDateCreated());
        accessTokenDomain.setToken(this.getToken());

        return accessTokenDomain;
    }

}
