package com.petshop.auth.adapter.output.repository.database;

import com.petshop.auth.application.domain.SenhaDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "senha", schema = "petshop_autenticacao")
@Entity(name = "senha")
public class SenhaDatabase implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String usuario;

    private String senha;

    @Column(name = "fk_id_usuario")
    private Long idUsuario;

    public SenhaDatabase(SenhaDomain senhaDomain) {
        this.setSenha(senhaDomain.getSenha());
        this.setId(senhaDomain.getId());
        this.setUsuario(senhaDomain.getUsuario());
        this.setIdUsuario(senhaDomain.getIdUsuario());
    }

    SenhaDomain toSenhaDomain() {
        SenhaDomain senhaDomain = new SenhaDomain();
        senhaDomain.setSenha(this.getSenha());
        senhaDomain.setId(this.getId());
        senhaDomain.setUsuario(this.getUsuario());
        senhaDomain.setIdUsuario(this.getIdUsuario());

        return senhaDomain;
    }

}
