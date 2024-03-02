package com.petshop.auth.adapter.input.http.senha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.input.http.ResponseHTTP;
import com.petshop.auth.application.domain.AuthenticationDomain;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.service.AuthenticationService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authentication",
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController {

    private final AuthenticationUsercase authenticationUsercase;

    private final ObjectMapper objectMapper;

    public AuthenticationController(AuthenticationUsercase authenticationUsercase, ObjectMapper objectMapper) {
        this.authenticationUsercase = authenticationUsercase;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = {"/", ""})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseHTTP save (@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        AuthenticationDomain authenticationDomain = authenticationRequest.toAuthenticationDomain();
        authenticationDomain = authenticationUsercase.create(authenticationDomain);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(authenticationDomain.getLogin(),
                authenticationRequest.idUser());

        return new ResponseHTTP("Sucesso ao criar senha", authenticationResponse);
    }

    @GetMapping(path = {"/", ""})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseHTTP> validate (@RequestParam(value = "login") String login,
                                    @RequestParam(value = "password") String password) throws Exception {

        AuthenticationDomain authenticationDomain = authenticationUsercase.getByLoginAndPassword(login, password);
        if (ObjectUtils.isEmpty(authenticationDomain)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(authenticationDomain.getLogin(),
                authenticationDomain.getIdUser());

        return ResponseEntity.ok().body(new ResponseHTTP("Sucesso ao criar senha", authenticationResponse)) ;
    }
}
