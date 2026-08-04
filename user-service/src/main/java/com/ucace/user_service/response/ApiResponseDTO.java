package com.ucace.user_service.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private boolean status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
}
