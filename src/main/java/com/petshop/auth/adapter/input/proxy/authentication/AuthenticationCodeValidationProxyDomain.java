package com.petshop.auth.adapter.input.proxy.authentication;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthenticationCodeValidationProxyDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String reference;

    private String code;

}
