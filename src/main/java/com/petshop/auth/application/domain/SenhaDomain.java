package com.petshop.auth.application.domain;

public class SenhaDomain {

    private Long id;

    private String usuario;

    private String senha;

    private Long idUsuario;

    public SenhaDomain () {
        super();
    }

    public SenhaDomain(Long id, String usuario, String senha, Long idUsuario) {
        this();
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
