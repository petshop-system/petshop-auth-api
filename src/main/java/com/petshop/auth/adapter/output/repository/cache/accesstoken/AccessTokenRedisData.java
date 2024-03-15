package com.petshop.auth.adapter.output.repository.cache.accesstoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "AccessToken", timeToLive = 300)
public class AccessTokenRedisData {

    @NonNull
    @Id
    private String token;

    private String value;

}
