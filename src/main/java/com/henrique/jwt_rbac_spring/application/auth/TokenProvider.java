package com.henrique.jwt_rbac_spring.application.auth;

import com.henrique.jwt_rbac_spring.domain.user.User;

public interface TokenProvider {

    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    String extractEmail(String token);
    boolean isTokenValid(String token, User user);
}
