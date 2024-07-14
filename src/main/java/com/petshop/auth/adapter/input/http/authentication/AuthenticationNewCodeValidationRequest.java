package com.petshop.auth.adapter.input.http.authentication;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record AuthenticationNewCodeValidationRequest(
        @JsonProperty("reference") String reference,

        @JsonProperty("digits") int digits

) implements Serializable {
}
