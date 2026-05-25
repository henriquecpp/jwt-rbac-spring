package com.henrique.jwt_rbac_spring.application.auth.exceptions;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token inválido ou expirado");
    }
}
