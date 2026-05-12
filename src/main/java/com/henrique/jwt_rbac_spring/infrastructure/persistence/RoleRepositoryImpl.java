package com.henrique.jwt_rbac_spring.infrastructure.persistence;

import com.henrique.jwt_rbac_spring.domain.rbac.Role;
import com.henrique.jwt_rbac_spring.domain.rbac.RoleRepository;
import com.henrique.jwt_rbac_spring.infrastructure.persistence.mapper.RoleMapper;
import com.henrique.jwt_rbac_spring.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository jpaRepository;
    private final RoleMapper roleMapper;

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(roleMapper::toDomain);
    }
}
