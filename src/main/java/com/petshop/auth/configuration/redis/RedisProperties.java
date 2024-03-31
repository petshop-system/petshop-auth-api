package com.petshop.auth.configuration.redis;

import lombok.Data;

import java.util.Map;

@Data
public class RedisProperties {

    private String host;

    private int port;

    private int database;

    private Cache cache;

    record Cache (Authentication authentication, AccessToken accessToken) {}

    record Authentication (String cacheName, int ttl, String prefix) {}

    record AccessToken (String cacheName, int ttl, String prefix) {}

}