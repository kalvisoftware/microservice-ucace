package com.ucace.role_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ucace.role_service.dto.RoleRequestDTO;
import com.ucace.role_service.dto.RoleResponseDTO;
import com.ucace.role_service.service.impl.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> saveRole(@RequestBody RoleRequestDTO request) {
        return new ResponseEntity<>(roleService.saveRole(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRole() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id, @RequestBody RoleRequestDTO request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }

}