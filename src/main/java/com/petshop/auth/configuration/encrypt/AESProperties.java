package com.petshop.auth.configuration.encrypt;

import lombok.Data;

@Data
public class AESProperties {

    private String algorithmic, key, initialVector;

}
