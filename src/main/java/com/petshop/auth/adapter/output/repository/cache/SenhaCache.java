package com.petshop.auth.adapter.output.repository.cache;

import com.petshop.auth.application.domain.SenhaDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Data
@RedisHash(timeToLive = 300, value = "Senha")
@AllArgsConstructor
@NoArgsConstructor
public class SenhaCache implements Serializable {

    @NonNull
    private Long id;

    @NonNull
    private String usuario;

    @NonNull
    private String senha;

    @NonNull
    private Long idUsuario;

    public SenhaCache(SenhaDomain senhaDomain) {
        this.setSenha(senhaDomain.getSenha());
        this.setId(senhaDomain.getId());
        this.setUsuario(senhaDomain.getUsuario());
        this.setIdUsuario(senhaDomain.getIdUsuario());
    }
}
