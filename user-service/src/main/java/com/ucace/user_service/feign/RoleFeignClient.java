package com.ucace.user_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.ucace.user_service.dto.RoleResponseDTO;

@FeignClient(name = "ROLE-SERVICE", url = "http://localhost:8081")

public interface RoleFeignClient {

    @GetMapping("/api/roles/{id}")
    public RoleResponseDTO getRoleById(@PathVariable("id") Long id);
}
