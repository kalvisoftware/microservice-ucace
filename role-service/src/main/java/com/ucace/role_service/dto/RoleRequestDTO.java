package com.ucace.role_service.dto;

import lombok.Data;

@Data
public class RoleRequestDTO {

    private String roleName;

    private String description;

    private Boolean status;

}