package com.example.Aerolinea.dto;

import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private ERole role;


    public static UserDTO fromUser(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());

        return user;
    }

    public UserDTO(Long id, String username, ERole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}