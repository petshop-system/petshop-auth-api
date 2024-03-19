package com.petshop.auth.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForbiddenException extends RuntimeException implements Supplier<ForbiddenException> {

    String message;

    public ForbiddenException(String message) {
        this.setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ForbiddenException get() {
        return this;
    }

}

