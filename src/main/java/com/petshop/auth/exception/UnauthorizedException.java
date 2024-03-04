package com.petshop.auth.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnauthorizedException extends RuntimeException implements Supplier<UnauthorizedException> {

    String message;

    public UnauthorizedException(String message) {
        this.setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public UnauthorizedException get() {
        return this;
    }

}
