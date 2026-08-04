package com.ucace.role_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucace.role_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleNameIgnoreCase(String roleName);

}