package com.petshop.auth.adapter.input.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public record ResponseHTTP (String message,
                            @JsonInclude(JsonInclude.Include.NON_NULL) Object result) {
}
