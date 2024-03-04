package com.petshop.auth.adapter.input.http;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonSerialize
@JsonDeserialize
public record ResponseHTTP(

        String message,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime date,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Object result) {
}
