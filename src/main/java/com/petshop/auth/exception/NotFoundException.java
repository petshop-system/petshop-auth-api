package com.petshop.auth.exception;

import lombok.Data;

@Data
public class NotFoundException extends Exception // implements Supplier<NotFoundException>
{

    String message;

    public NotFoundException(String message) {
        this.setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

//    @Override
    public NotFoundException get() {
        return this;
    }

}