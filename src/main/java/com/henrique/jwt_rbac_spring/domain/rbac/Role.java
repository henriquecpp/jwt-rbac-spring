package com.henrique.jwt_rbac_spring.domain.rbac;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Role {

    private final UUID id;
    private final String name;
    private final Set<Permission> permissions;

    public Role(UUID id, String name, Set<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions != null ? permissions : Collections.emptySet();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Set<Permission> getPermissions() { return Collections.unmodifiableSet(permissions); }

    // Retorna todas as authorities desta role: ROLE_ADMIN, admin:read, etc.
    public Set<String> getAuthorities() {
        var authorities = new java.util.HashSet<String>();

        authorities.add("ROLE_" + this.name);

        permissions.stream()
                .map(Permission::getName)
                .forEach(authorities::add);

        return Collections.unmodifiableSet(authorities);
    }
}