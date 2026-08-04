package com.ucace.user_service.dto;

import lombok.Data;

@Data
public class RoleResponseDTO {

    private Long id;
    private String roleName;
    private String description;
    private Boolean status;
}