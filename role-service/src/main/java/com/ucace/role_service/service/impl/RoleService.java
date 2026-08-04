package com.ucace.role_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucace.role_service.dto.RoleRequestDTO;
import com.ucace.role_service.dto.RoleResponseDTO;

@Service
public interface RoleService {

    RoleResponseDTO saveRole(RoleRequestDTO role);

    List<RoleResponseDTO> getAllRoles();

    RoleResponseDTO getRoleById(Long id);

    RoleResponseDTO updateRole(Long id, RoleRequestDTO role);

    String deleteRole(Long id);
}
