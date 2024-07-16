package com.petshop.auth.adapter.input.proxy.authentication;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthenticationNewCodeValidationProxyDomain {

    private String reference;

    private int digits;

}
