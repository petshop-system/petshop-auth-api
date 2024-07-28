package com.petshop.auth.application.domain;

public class AuthenticationNewCodeValidationDomain {

    private String reference;

    private int digits;

    public AuthenticationNewCodeValidationDomain() {
    }

    public AuthenticationNewCodeValidationDomain(String reference, int digits) {
        this.reference = reference;
        this.digits = digits;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }
}
