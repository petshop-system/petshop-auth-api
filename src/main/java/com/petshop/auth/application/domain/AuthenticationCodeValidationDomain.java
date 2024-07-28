package com.petshop.auth.application.domain;

public class AuthenticationCodeValidationDomain {

    private String reference;

    private String code;

    public AuthenticationCodeValidationDomain() {
    }

    public AuthenticationCodeValidationDomain(String reference, String code) {
        this.reference = reference;
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
