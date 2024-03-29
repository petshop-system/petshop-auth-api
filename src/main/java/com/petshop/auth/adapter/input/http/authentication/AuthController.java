package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.input.http.ResponseHTTP;
import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.utils.AESEncryptionUtils;
import com.petshop.auth.utils.JWTUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth",
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String REQUEST_ID = "requestID";

    private final AuthenticationUsercase authenticationUsercase;

    private final AuthorizationUserCase authorizationUserCase;

    private final ObjectMapper objectMapper;

    private final AESEncryptionUtils aesEncryptionUtils;

    private final JWTUtils jwtUtils;

    public AuthController(AuthenticationUsercase authenticationUsercase,
                          @Qualifier("authorizationUserCase") AuthorizationUserCase authorizationUserCase,
                          ObjectMapper objectMapper,
                          AESEncryptionUtils aesEncryptionUtils,
                          @Qualifier("jwtUtils") JWTUtils jwtUtils) {
        this.authenticationUsercase = authenticationUsercase;
        this.authorizationUserCase = authorizationUserCase;
        this.objectMapper = objectMapper;
        this.aesEncryptionUtils = aesEncryptionUtils;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(path = {"/signup/", "/signup"})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseHTTP save (@RequestBody String body,
                              @RequestHeader(name = "request_id") String requestID) throws Exception {

        AuthenticationRequest authenticationRequest = objectMapper.readValue(aesEncryptionUtils.decrypt(body), AuthenticationRequest.class);
        AuthenticationDomain authenticationDomain = authenticationRequest.toAuthenticationDomain();
        authenticationDomain = authenticationUsercase.create(authenticationDomain);

        String jws = jwtUtils.generateToken(authenticationDomain.getLogin());
        authorizationUserCase.getNewAccessToken(jws, authenticationDomain);

        String message = "sucesso ao criar login";
        logger.atInfo()
                .setMessage(message)
                .addKeyValue("id_user", authenticationRequest.idUser())
                .addKeyValue(REQUEST_ID, requestID)
                .log();

        return new ResponseHTTP(message, LocalDateTime.now(), jws);
    }

    @GetMapping(path = {"/signin/", "/signin"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseHTTP> login (@RequestHeader(value = "credentials") String credentials,
                                               @RequestHeader(name = "request_id") String requestID) throws Exception {

        Map loginPassword = objectMapper.readValue(aesEncryptionUtils.decrypt(credentials), HashMap.class);
        String login = (String) loginPassword.get("login");
        String password = (String) loginPassword.get("password");

        AuthenticationDomain authenticationDomain = authenticationUsercase.login(login, password);
        if (ObjectUtils.isEmpty(authenticationDomain)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jws = jwtUtils.generateToken(login);
        authorizationUserCase.getNewAccessToken(jws, authenticationDomain);

        String message = "sucesso ao logar";
        logger.atInfo()
                .setMessage(message)
                .addKeyValue("id_user", authenticationDomain.getIdUser())
                .addKeyValue(REQUEST_ID, requestID)
                .log();

        return ResponseEntity.ok().body(new ResponseHTTP(message, LocalDateTime.now(), jws)) ;
    }

    @PostMapping(path = {"/signout/", "/signout"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseHTTP> logout (@RequestHeader("Authorization") String token,
                                                @RequestHeader(name = "request_id") String requestID) throws Exception {

        authorizationUserCase.invalidateAccessToken(token);

        String message = "logout com sucesso";
        logger.atInfo()
                .setMessage(message)
                .addKeyValue("token", token)
                .addKeyValue(REQUEST_ID, requestID)
                .log();

        return ResponseEntity.ok().body(new ResponseHTTP(message, LocalDateTime.now(), null));
    }

    @GetMapping(path = {"/doauth/{access}/", "/doauth/{access}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> doAuthorization(@RequestHeader("Authorization") String token,
                                          @PathVariable("access") String access,
                                             @RequestHeader(name = "request_id") String requestID) {

        if (!jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        authorizationUserCase.doAuthorization(token,
                new AccessDomain(access));

        String message = "autorizado com sucesso";
        logger.atInfo()
                .setMessage(message)
                .addKeyValue("token", token)
                .addKeyValue(REQUEST_ID, requestID)
                .log();

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = {"/crypt/", "/crypt"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> crypt(@RequestHeader("Authorization") String token,
                                   @RequestBody String body) {

        String encrypt = aesEncryptionUtils.encrypt(body);

        return ResponseEntity.ok().body(encrypt);
    }

}
