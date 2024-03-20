package com.petshop.auth.adapter.input.http;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonSerialize
@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ResponseHTTP(

        String message,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,

        Object result) {
}
