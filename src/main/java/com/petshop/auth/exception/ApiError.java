package com.petshop.auth.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Data
public class ApiError {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> result;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<String> messages;

    private ApiError() {
        this.date = LocalDateTime.now();
    }

    public ApiError(Map<String, Object> result) {
        this();
        this.result = result;
    }

    public ApiError(String message) {
        this();
        this.message = message;
    }

    public ApiError(Collection<String> messages) {
        this();
        this.messages = messages;
    }

}