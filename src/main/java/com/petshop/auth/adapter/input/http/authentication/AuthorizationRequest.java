package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.petshop.auth.application.domain.ProfileDomain;

public record AuthorizationRequest (

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("access_action")
        String accessAction

) {
}
