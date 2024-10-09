package com.example.Aerolinea.dto.response;

import com.example.Aerolinea.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String username;
    private String email;
    private ERole role;
    private String message;
}