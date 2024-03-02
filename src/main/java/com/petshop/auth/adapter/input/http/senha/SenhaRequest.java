package com.petshop.auth.adapter.input.http.senha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.petshop.auth.application.domain.SenhaDomain;

public record SenhaRequest(@JsonProperty("usuario") String usuario,
                           @JsonProperty("id") Long id,
                           @JsonProperty("senha") String senha,
                           @JsonProperty("id_usuario") Long idUsuario) {

    SenhaDomain toSenhaDomain() {
        SenhaDomain senhaDomain = new SenhaDomain();
        senhaDomain.setUsuario(this.usuario);
        senhaDomain.setId(this.id);
        senhaDomain.setSenha(this.senha);
        senhaDomain.setIdUsuario(this.idUsuario);

        return senhaDomain;
    }
}
