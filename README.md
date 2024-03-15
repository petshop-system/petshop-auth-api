# Petshop-auth-api

Project to handle the authentication and authorization for Petshop-System.

## Ferramentas

* AES CBC 128;
* Dockerfile;
* Docker-compose;
* Java;
* JWT (JWS);
* Makefile;
* Postgresql;
* Redis;
* Springboot;

## Fluxo

### Fluxo deAuthenticação 

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

### Fluxo de Autorização

![Petshop-Authorization-Flow.png](./content%2FPetshop-Authorization-Flow.png)

``` 
title Petshop-Authorization-Flow

Client->AuthAPI: Post /auth/doauth/{access}/ -H 'Authorization: [jwt_token]'
AuthAPI->AuthAPI: Validate JWT Token
AuthAPI->AuthAPI: Validate access to resource
AuthAPI->Client: 
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

## Banco de dados

![petshop_auth-2024-03-14T23_23_52.png](./content%2Fpetshop_auth-2024-03-14T23_23_52.png)



