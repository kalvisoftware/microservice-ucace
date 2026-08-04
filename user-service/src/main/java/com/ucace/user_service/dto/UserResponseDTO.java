package com.ucace.user_service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String userName;

    private String email;

    private Long mobileNo;

    private Long roleId;

    private String roleName;

    private Boolean status;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}