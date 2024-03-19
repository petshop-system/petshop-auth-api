package com.petshop.auth.application.domain;

public class AccessDomain {

    private String action;

    private String description;

    public AccessDomain() {
    }

    public AccessDomain(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
