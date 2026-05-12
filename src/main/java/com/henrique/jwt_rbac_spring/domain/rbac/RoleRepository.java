package com.henrique.jwt_rbac_spring.domain.rbac;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);
}
