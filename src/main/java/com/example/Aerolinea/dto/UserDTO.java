package com.example.Aerolinea.dto;

import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private ERole role;
    private List<String> authorities;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setAuthorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return dto;
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        return user;
    }

    public UserDTO(Long id, String username, ERole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}