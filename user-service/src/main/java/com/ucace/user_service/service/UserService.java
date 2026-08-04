package com.ucace.user_service.service;

import com.ucace.user_service.dto.UserRequestDTO;
import com.ucace.user_service.dto.UserResponseDTO;
import java.util.List;

public interface UserService {

    UserResponseDTO saveUser(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO request);

    String deleteUser(Long id);

}