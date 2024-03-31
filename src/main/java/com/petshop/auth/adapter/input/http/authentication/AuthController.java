package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.input.http.ResponseHTTP;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyDomain;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyService;
import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.utils.AESEncryptionUtils;
import com.petshop.auth.utils.JWTUtils;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;
import org.apache.commons.lang3.ObjectUtils;
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

    private final AuthenticationProxyService authenticationProxyService;

    private final AuthorizationUserCase authorizationUserCase;

    private final ObjectMapper objectMapper;

    private final AESEncryptionUtils aesEncryptionUtils;

    private final JWTUtils jwtUtils;

    private final AuthenticationConverterMapper authenticationConverterMapper;

    public AuthController(@Qualifier("authenticationProxyService") AuthenticationProxyService authenticationProxyService,
                          @Qualifier("authorizationUserCase") AuthorizationUserCase authorizationUserCase,
                          ObjectMapper objectMapper,
                          AESEncryptionUtils aesEncryptionUtils,
                          @Qualifier("jwtUtils") JWTUtils jwtUtils,
                          AuthenticationConverterMapper authenticationConverterMapper) {
        this.authenticationProxyService = authenticationProxyService;
        this.authorizationUserCase = authorizationUserCase;
        this.objectMapper = objectMapper;
        this.aesEncryptionUtils = aesEncryptionUtils;
        this.jwtUtils = jwtUtils;
        this.authenticationConverterMapper = authenticationConverterMapper;
    }

    @PostMapping(path = {"/signup/", "/signup"})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseHTTP save (@RequestBody String body) throws Exception {

        AuthenticationRequest authenticationRequest = objectMapper.readValue(aesEncryptionUtils.decrypt(body),
                AuthenticationRequest.class);

        AuthenticationProxyDomain apd = authenticationConverterMapper
                .toAuthenticationProxyDomain(authenticationRequest);

        AuthenticationDomain authenticationDomain = authenticationConverterMapper
                .toAuthenticationDomain(authenticationProxyService.create(apd));

        String jws = jwtUtils.generateToken(authenticationDomain.getLogin());
        authorizationUserCase.getNewAccessToken(jws, authenticationDomain);

        return new ResponseHTTP("sucesso ao criar login", LocalDateTime.now(), jws);
    }

    @GetMapping(path = {"/signin/", "/signin"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseHTTP> login (@RequestHeader(value = "credentials") String credentials) throws Exception {

        Map loginPassword = objectMapper.readValue(aesEncryptionUtils.decrypt(credentials), HashMap.class);
        String login = (String) loginPassword.get("login");
        String password = (String) loginPassword.get("password");

        AuthenticationProxyDomain apd = authenticationProxyService.login(login, password);
        AuthenticationDomain authenticationDomain = authenticationConverterMapper
                .toAuthenticationDomain(apd);
        if (ObjectUtils.isEmpty(authenticationDomain)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jws = jwtUtils.generateToken(login);
        authorizationUserCase.getNewAccessToken(jws, authenticationDomain);

        return ResponseEntity.ok().body(new ResponseHTTP("sucesso ao logar", LocalDateTime.now(), jws)) ;
    }

    @PostMapping(path = {"/signout/", "/signout"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseHTTP> logout (@RequestHeader("Authorization") String token) throws Exception {

        authorizationUserCase.invalidateAccessToken(token);
        return ResponseEntity.ok().body(new ResponseHTTP("logout com sucesso", LocalDateTime.now(), null));
    }

    @GetMapping(path = {"/doauth/{access}/", "/doauth/{access}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> doAuthorization(@RequestHeader("Authorization") String token,
                                          @PathVariable("access") String access) {

        if (!jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        authorizationUserCase.doAuthorization(token,
                new AccessDomain(access));

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = {"/crypt/", "/crypt"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> crypt(@RequestHeader("Authorization") String token,
                                   @RequestBody String body) {

//        if (!jwtUtils.validateJwtToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

        String encrypt = aesEncryptionUtils.encrypt(body);

        return ResponseEntity.ok().body(encrypt);
    }

}
