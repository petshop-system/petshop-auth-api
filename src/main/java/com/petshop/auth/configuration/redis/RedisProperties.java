package com.petshop.auth.configuration.redis;

import lombok.Data;

@Data
public class RedisProperties {

    private String host;

    private int port;

    private int database;

}