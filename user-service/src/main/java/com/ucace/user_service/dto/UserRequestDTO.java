package com.ucace.user_service.dto;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String userName;

    private String password;

    private String email;

    private Long mobileNo;

    private Long roleId;

    private Boolean status;

}