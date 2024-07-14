package com.petshop.auth.adapter.input.proxy.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthenticationCodeValidationProxyDomain {

    private String reference;

    private String code;

}
