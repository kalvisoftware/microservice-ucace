package com.ucace.user_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ucace.user_service.dto.RoleResponseDTO;
import com.ucace.user_service.dto.UserRequestDTO;
import com.ucace.user_service.dto.UserResponseDTO;
import com.ucace.user_service.entity.User;
import com.ucace.user_service.exception.ResourceAlreadyExistsException;
import com.ucace.user_service.exception.ResourceNotFoundException;
import com.ucace.user_service.feign.RoleFeignClient;
import com.ucace.user_service.repository.UserRepository;
import com.ucace.user_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleFeignClient roleFeignClient;

    public UserServiceImpl(UserRepository userRepository, RoleFeignClient roleFeignClient) {
        this.userRepository = userRepository;
        this.roleFeignClient = roleFeignClient;
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO request) {

        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        if (userRepository.findByMobileNo(request.getMobileNo()).isPresent()) {
            throw new ResourceAlreadyExistsException("Mobile number already exists");
        }

        validateRole(request.getRoleId());
        User user = convertToEntity(request);

        User savedUser = userRepository.save(user);

        UserResponseDTO dto = convertToDTO(savedUser);

        setRoleName(dto, savedUser.getRoleId());

        return dto;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    UserResponseDTO dto = convertToDTO(user);

                    setRoleName(dto, user.getRoleId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        UserResponseDTO dto = convertToDTO(user);
        setRoleName(dto, user.getRoleId());
        return dto;
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        validateRole(request.getRoleId());
        // Duplicate Username Check
        userRepository.findByUserName(request.getUserName())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("Username already exists");
                });

        // Duplicate Email Check
        userRepository.findByEmail(request.getEmail())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("Email already exists");
                });

        // Duplicate Mobile Check
        userRepository.findByMobileNo(request.getMobileNo())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("Mobile number already exists");
                });

        User updatedUser = updateEntity(existingUser, request);

        UserResponseDTO dto = convertToDTO(updatedUser);

        setRoleName(dto, updatedUser.getRoleId());

        return dto;
    }

    @Override
    public String deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        userRepository.delete(existingUser);

        return "User Deleted Successfully";
    }

    private User convertToEntity(UserRequestDTO request) {

        User user = new User();

        user.setUserName(request.getUserName().trim());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail().trim());
        user.setMobileNo(request.getMobileNo());
        user.setRoleId(request.getRoleId());
        user.setStatus(request.getStatus());

        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());

        return user;
    }

    private UserResponseDTO convertToDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setMobileNo(user.getMobileNo());
        dto.setRoleId(user.getRoleId());
        dto.setStatus(user.getStatus());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setUpdatedDate(user.getUpdatedDate());

        return dto;
    }

    private User updateEntity(User existingUser, UserRequestDTO request) {

        existingUser.setUserName(request.getUserName().trim());
        existingUser.setPassword(request.getPassword());
        existingUser.setEmail(request.getEmail().trim());
        existingUser.setMobileNo(request.getMobileNo());
        existingUser.setRoleId(request.getRoleId());
        existingUser.setStatus(request.getStatus());
        existingUser.setUpdatedDate(LocalDateTime.now());

        return existingUser;
    }

    private void validateRole(Long roleId) {
        RoleResponseDTO role = roleFeignClient.getRoleById(roleId);
        if (role == null) {
            throw new ResourceNotFoundException("Role not found");
        }
    }

    private void setRoleName(UserResponseDTO dto, Long roleId) {

        RoleResponseDTO role = roleFeignClient.getRoleById(roleId);

        if (role != null) {
            dto.setRoleName(role.getRoleName());
        }
    }
}