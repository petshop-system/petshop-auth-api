package com.petshop.auth.adapter.input.http.authentication;


import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationNewCodeValidationRequest(

        @JsonProperty("reference") String reference,

        @JsonProperty("digits") int digits

) {

}
