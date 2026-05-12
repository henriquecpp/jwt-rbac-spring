package com.henrique.jwt_rbac_spring.domain.user;

import com.henrique.jwt_rbac_spring.domain.rbac.Role;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String name;
    private final String email;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final Set<Role> roles;

    public User(UUID id, String name, String email, String password,
                boolean enabled, boolean accountNonLocked, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.roles = roles != null ? roles : Collections.emptySet();
    }

    // Agrega todas as authorities de todas as roles do usuário
    public Set<String> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getAuthorities().stream())
                .collect(java.util.stream.Collectors.toSet());
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isEnabled() { return enabled; }
    public boolean isAccountNonLocked() { return accountNonLocked; }
    public Set<Role> getRoles() { return Collections.unmodifiableSet(roles); }
}