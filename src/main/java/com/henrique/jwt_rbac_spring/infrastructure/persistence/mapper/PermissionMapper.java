package com.henrique.jwt_rbac_spring.infrastructure.persistence.mapper;

import com.henrique.jwt_rbac_spring.domain.rbac.Permission;
import com.henrique.jwt_rbac_spring.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public Permission toDomain(PermissionEntity entity) {
        return new Permission(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}