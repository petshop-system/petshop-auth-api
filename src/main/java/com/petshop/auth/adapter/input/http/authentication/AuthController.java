package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.input.http.ResponseHTTP;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationCodeValidationProxyDomain;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationNewCodeValidationProxyDomain;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyDomain;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyService;
import com.petshop.auth.application.domain.AccessDomain;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthorizationUserCase;
import com.petshop.auth.exception.ForbiddenException;
import com.petshop.auth.utils.AESEncryptionUtils;
import com.petshop.auth.utils.JWTUtils;
import com.petshop.auth.utils.converter.AuthenticationConverterMapper;
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

    public static final String Request_ID_Header = "X-Request-Id";

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
    public ResponseHTTP save (@RequestBody String body,
                              @RequestHeader(name = Request_ID_Header) String requestID) throws Exception {

        AuthenticationRequest authenticationRequest = objectMapper.readValue(aesEncryptionUtils.decrypt(body),
                AuthenticationRequest.class);

        AuthenticationProxyDomain apd = authenticationConverterMapper
                .toAuthenticationProxyDomain(authenticationRequest);

        AuthenticationDomain authenticationDomain = authenticationConverterMapper
                .toAuthenticationDomain(authenticationProxyService.create(apd));

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
                                               @RequestHeader(name = Request_ID_Header) String requestID) throws Exception {

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
                                                @RequestHeader(name = Request_ID_Header) String requestID) throws Exception {

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
                                             @RequestHeader(name = Request_ID_Header) String requestID) {

        if (!jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        authorizationUserCase.doAuthorization(token,
                new AccessDomain(access));

        logger.atInfo()
                .setMessage("autorizado com sucesso")
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

    @PostMapping(path = {"/new-code-validation/", "/new-code-validation"})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseHTTP newCodeValidation (@RequestBody AuthenticationNewCodeValidationRequest body,
                              @RequestHeader(name = Request_ID_Header) String requestID) throws Exception {

        String codeJson = authenticationProxyService.getCodeValidation(body.reference(), body.digits());
        AuthenticationCodeValidationProxyDomain codeValidationProxyDomain =
                objectMapper.readValue(codeJson, AuthenticationCodeValidationProxyDomain.class);

        AuthenticationCodeValidationResponse codeValidationResponse =
                authenticationConverterMapper.toAuthenticationCodeValidationResponse(codeValidationProxyDomain);

        String message = "success to create a new code validation.";
        logger.atInfo()
                .setMessage(message)
                .addKeyValue("reference", codeValidationResponse.reference())
                .addKeyValue(REQUEST_ID, requestID)
                .log();

        return new ResponseHTTP(message, LocalDateTime.now(), codeValidationResponse);
    }

    @PostMapping(path = {"/validate-code-validation/", "/validate-code-validation"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseHTTP validateCodeValidation (@RequestBody AuthenticationCodeValidationRequest body,
                                           @RequestHeader(name = Request_ID_Header) String requestID) throws Exception {

        String codeJson = authenticationProxyService.getCodeValidation(body.reference(), 0);
        if (ObjectUtils.isEmpty(codeJson)) {
            throw new ForbiddenException("wrong code notification");
        }

        AuthenticationCodeValidationProxyDomain codeValidationProxyDomain =
                objectMapper.readValue(codeJson, AuthenticationCodeValidationProxyDomain.class);

        authenticationProxyService.validateCodeValidation(body.reference(), codeValidationProxyDomain.getReference(),
                body.code(), codeValidationProxyDomain.getCode());

        String message = "success to validate the code.";
        logger.atInfo()
                .setMessage(message)
                .addKeyValue("reference", body.reference())
                .addKeyValue(REQUEST_ID, requestID)
                .log();

        return new ResponseHTTP(message, LocalDateTime.now(), null);
    }

}
