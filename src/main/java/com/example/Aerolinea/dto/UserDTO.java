package com.example.Aerolinea.dto;

import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;

public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    private ERole role;

    public UserDTO() {}

    public UserDTO(Integer id, String username, String email, ERole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public static User toUser(UserDTO userDTO) {
        return new User.Builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }
}



