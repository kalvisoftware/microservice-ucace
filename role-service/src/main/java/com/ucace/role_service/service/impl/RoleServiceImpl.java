package com.ucace.role_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ucace.role_service.dto.RoleRequestDTO;
import com.ucace.role_service.dto.RoleResponseDTO;
import com.ucace.role_service.entity.Role;
import com.ucace.role_service.exception.ResourceAlreadyExistsException;
import com.ucace.role_service.exception.ResourceNotFoundException;
import com.ucace.role_service.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleResponseDTO saveRole(RoleRequestDTO request) {

        Optional<Role> existingRole = roleRepository.findByRoleNameIgnoreCase(request.getRoleName().trim());

        if (existingRole.isPresent()) {
            throw new ResourceAlreadyExistsException("Role already exists");
        }

        Role role = convertToEntity(request);

        Role savedRole = roleRepository.save(role);

        return convertToDTO(savedRole);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {

        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        return convertToDTO(role);
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO request) {

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        existingRole.setRoleName(request.getRoleName().trim());
        existingRole.setDescription(request.getDescription());
        existingRole.setStatus(request.getStatus());
        existingRole.setUpdatedDate(LocalDateTime.now());

        Role updatedRole = roleRepository.save(existingRole);

        return convertToDTO(updatedRole);
    }

    @Override
    public String deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        roleRepository.delete(role);

        return "Role deleted successfully";
    }

    private Role convertToEntity(RoleRequestDTO request) {

        Role role = new Role();

        role.setRoleName(request.getRoleName().trim());
        role.setDescription(request.getDescription());
        role.setStatus(request.getStatus());
        role.setCreatedDate(LocalDateTime.now());
        role.setUpdatedDate(LocalDateTime.now());

        return role;
    }

    private RoleResponseDTO convertToDTO(Role role) {

        RoleResponseDTO dto = new RoleResponseDTO();

        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());
        dto.setStatus(role.getStatus());
        dto.setCreatedDate(role.getCreatedDate());
        dto.setUpdatedDate(role.getUpdatedDate());

        return dto;
    }
}