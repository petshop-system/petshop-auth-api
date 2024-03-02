package com.petshop.auth.application.domain;

public class AuthenticationDomain {

    private Long id;

    private String login;

    private String password;

    private Long idUser;

    public AuthenticationDomain() {
        super();
    }

    public AuthenticationDomain(Long id, String login, String password, Long idUser) {
        this();
        this.id = id;
        this.login = login;
        this.password = password;
        this.idUser = idUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
