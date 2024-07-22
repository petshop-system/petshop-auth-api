package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record AuthenticationCodeValidationResponse(
        @JsonProperty("reference") String reference,

        @JsonProperty("code") String code
) implements Serializable {

}
