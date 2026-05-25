package com.henrique.jwt_rbac_spring.application.auth;

import com.henrique.jwt_rbac_spring.application.auth.dto.AuthResponse;
import com.henrique.jwt_rbac_spring.application.auth.dto.LoginRequest;
import com.henrique.jwt_rbac_spring.application.auth.dto.RegisterRequest;
import com.henrique.jwt_rbac_spring.application.auth.exceptions.EmailAlreadyExistsException;
import com.henrique.jwt_rbac_spring.application.auth.exceptions.InvalidTokenException;
import com.henrique.jwt_rbac_spring.domain.rbac.RoleRepository;
import com.henrique.jwt_rbac_spring.domain.user.User;
import com.henrique.jwt_rbac_spring.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${app.jwt.access-token-expiration-ms}")
    private long accessTokenExpiration;

    public AuthResponse login(LoginRequest request) {
        // Delega a validação de credenciais para o Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        String accessToken  = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        return AuthResponse.of(accessToken, refreshToken, accessTokenExpiration / 1000);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        var defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER não encontrada"));

        var user = new User(
                null,
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                true,
                true,
                Set.of(defaultRole)
        );

        userRepository.save(user);

        return login(new LoginRequest(request.email(), request.password()));
    }

    public AuthResponse refresh(String refreshToken) {
        String email = tokenProvider.extractEmail(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        if (!tokenProvider.isTokenValid(refreshToken, user)) {
            throw new InvalidTokenException();
        }

        String newAccessToken = tokenProvider.generateAccessToken(user);

        return AuthResponse.of(newAccessToken, refreshToken, accessTokenExpiration / 1000);
    }
}