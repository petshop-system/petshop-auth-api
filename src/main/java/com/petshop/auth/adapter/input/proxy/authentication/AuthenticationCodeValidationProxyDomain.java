package com.petshop.auth.adapter.input.proxy.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationCodeValidationProxyDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String reference;

    private String code;

}
