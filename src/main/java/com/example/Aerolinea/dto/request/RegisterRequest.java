package com.example.Aerolinea.dto.request;

import com.example.Aerolinea.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    String username;
    String email;
    String password;
    ERole role;
}
