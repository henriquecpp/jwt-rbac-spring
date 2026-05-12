package com.henrique.jwt_rbac_spring.infrastructure.persistence.mapper;

import com.henrique.jwt_rbac_spring.domain.user.User;
import com.henrique.jwt_rbac_spring.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

    public User toDomain(UserEntity entity) {
        var roles = entity.getRoles().stream()
                .map(roleMapper::toDomain)
                .collect(Collectors.toSet());

        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.isEnabled(),
                entity.isAccountNonLocked(),
                roles
        );
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .accountNonLocked(user.isAccountNonLocked())
                .build();
    }
}
