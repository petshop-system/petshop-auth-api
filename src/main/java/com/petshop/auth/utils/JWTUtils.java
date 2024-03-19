package com.petshop.auth.utils;

import com.petshop.auth.configuration.jwt.JWTProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

public class JWTUtils {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    private final JWTProperties jwtProperties;


    public JWTUtils(@Qualifier("jwtProperties") JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getJwtSecret()));
    }

    public String generateToken(String subject) {
        var now = Instant.now();
        return Jwts.builder()
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(jwtProperties.getJwtExpirationMS())))
                .signWith(key())
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {

            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
