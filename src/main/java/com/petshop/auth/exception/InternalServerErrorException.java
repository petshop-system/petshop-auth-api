package com.petshop.auth.exception;

import lombok.Data;

@Data
public class InternalServerErrorException  extends RuntimeException //implements Supplier<InternalServerErrorException>
{

    String message;

    public InternalServerErrorException(String message) {
        this.setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

//    @Override
    public InternalServerErrorException get() {
        return this;
    }

}