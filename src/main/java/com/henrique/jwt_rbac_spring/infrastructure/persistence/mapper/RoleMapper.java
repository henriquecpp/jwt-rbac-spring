package com.henrique.jwt_rbac_spring.infrastructure.persistence.mapper;

import com.henrique.jwt_rbac_spring.domain.rbac.Role;
import com.henrique.jwt_rbac_spring.infrastructure.persistence.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public Role toDomain(RoleEntity entity) {
        var permissions = entity.getPermissions().stream()
                .map(permissionMapper::toDomain)
                .collect(Collectors.toSet());

        return new Role(
                entity.getId(),
                entity.getName(),
                permissions
        );
    }
}
