# Petshop-auth-api

Projeto para gerir o fluxo de autenticação e autorização do Petshop-System.

## Ferramentas

* AES CBC 128;
* Dockerfile;
* Docker-compose;
* Java 17;
* JWT (JWS);
* Makefile;
* Postgresql;
* Redis;
* Springboot;

## Fluxos

### Fluxo de Autenticação 

![Petshop-Authentication-Flow.png](./content%2FPetshop-Authentication-Flow.png)

```
title Petshop-Authentication-Flow

Client->AuthAPI: Post /auth/signin -H 'credentials: enc[login]'
note right of Client: Send encrypted login (AES CBC 128)
AuthAPI->AuthAPI: Decrypt login (AES CBC 128)
AuthAPI->AuthAPI: Validate login
AuthAPI->AuthAPI: Generate JWT (JWS)
AuthAPI->Cache: store token
AuthAPI->Client: Auth Response with JWT
note right of Client: Create Auth-Cookie 'petshop-auth: jwt'
```

Exemplo request

```
curl --location 'http://localhost:5004/petshop-auth-api/auth/signin' \
--header 'credentials: Yv0Bn4b/qTC0b+fZoB1dVkDtlGyhs7d+z2Fylp3Rn28ueP2Lz0lCqhYHbVgXpwAOWgq2FXIdU9vq5jgExv/9lBh1B05Q5snL12tDOO/R/Dg='
```

Exemplo de credentials

```
{
    "login": "admin",
    "password": "1234"
}
```

### Fluxo de Autorização

![Petshop-Authorization-Flow.png](./content%2FPetshop-Authorization-Flow.png)

``` 
title Petshop-Authorization-Flow

Client->AuthAPI: Post /auth/doauth/{access}/ -H 'Authorization: [jwt_token]'
AuthAPI->AuthAPI: Validate JWT Token
AuthAPI->AuthAPI: Validate access to resource
AuthAPI->Client: 
```

Exemplo request

```
curl --location 'http://localhost:5004/petshop-auth-api/auth/doauth/CUSTOMER_REGISTER/' \
--header 'Authorization: eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0QHBldHNob3BzeXN0ZW0uY29tIiwiaWF0IjoxNzEwMzgwMTUyLCJleHAiOjE3MTAzODE5NTJ9.nQ2t5HOEE3ys0kpAwFb6q7OMkcK5BG4Y0yEcQhExjGXGHUQ6_TJrSzyIsqRyxxEz'
```

### Fluxo de Registro 

![Petshop-Signup-Flow.png](./content%2FPetshop-Signup-Flow.png)

```
title Petshop-Signup-Flow

Client->AuthAPI: Post /auth/signup --data 'enc[body]'
note right of Client: Send encrypted body (AES CBC 128)
AuthAPI->AuthAPI: Decrypt body (AES CBC 128)
AuthAPI->AuthAPI: Validate values
AuthAPI->DB: store values
AuthAPI->AuthAPI: Generate JWT (JWS)
AuthAPI->Cache: store token
AuthAPI->Client: Auth Response with JWT
```

Exemplo request

```
curl --location 'http://localhost:5004/petshop-auth-api/auth/signup/' \
--header 'Content-Type: text/plain' \
--data 'LTHXtk4XKvQy65gBzUfz1YlvHC7K2zsghX0mJADZMfjjU4Sz3TGzSVeDeMnvdExPAY3TKksPUwQGCk/AukP7UZ2ZJbdeLM+KBEmLmtv+pHYdYHbH5opjE+a0G1WCmM6m4/KkcVnQQOgEgmM8rkpP4kGAqUl2BLyJsw8i9DCpGa0/aC9n/5OUMmVzQDc4Sb4L'
```

Exemplo de credentials

```
{
    "login": admin,
    "password": "1234",
    "id_user": 1,
    "profile: "USER",
    "active": true
}
```

## Banco de dados

![petshop_auth-2024-03-14T23_23_52.jpg](./content%2Fpetshop_auth-2024-03-14T23_23_52.jpg)

## Referências

* [Encryption and decryption of data at blazing speed using Spring Data JPA](https://medium.com/@deependra.chourasia/encryption-and-decryption-of-data-at-blazing-speed-using-spring-data-jpa-a4c36b84588e);
* [Métodos de autenticação e autorização de acesso](https://medium.com/@pedrodev/m%C3%A9todos-de-autentica%C3%A7%C3%A3o-e-autoriza%C3%A7%C3%A3o-de-acesso-f8bd6c58ead0);
* [cryptii](https://cryptii.com/pipes/hmac);
* [Tutorial: Create and Verify JWTs in Java](https://developer.okta.com/blog/2018/10/31/jwts-with-java);
* [Spring Boot Security and JWT tutorial with example](Spring Boot Security and JWT tutorial with example);
* [Supercharge Java Authentication with JSON Web Tokens (JWTs)](https://www.baeldung.com/java-json-web-tokens-jjwt);



