package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("usuario")
        String usuario,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("id_usuario")
        Long idUsuario) {

}
