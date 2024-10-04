package com.example.Aerolinea.integrationTest.controller;

import com.example.Aerolinea.controller.UserController;
import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerIntegrationTest {
    @Mock
    private UserService userService;
    private MockMvc mockMvc;
    private User user1;
    private User user2;


    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("Sofia");
        user1.setPassword("Conejitos");
        user1.setRole(ERole.ROLE_USER);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("Isabel");
        user2.setPassword("Perritos");
        user2.setRole(ERole.ROLE_USER);
    }

    @Test
    public void CreateUser() throws  Exception{
        when(userService.createUser(any(User.class))).thenReturn(user1);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user1);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Sofia"))
                .andExpect(jsonPath("$.password").value("Conejitos"));
    }

    @Test
    public void GetAllUser() throws Exception{
        User user1 = new User();
        user1.setUsername("Sofia");
        user1.setPassword("Conejitos");

        User user2 = new User();
        user2.setUsername("Isabel");
        user2.setPassword("Perritos");
        List<User> userList = Arrays.asList(user1,user2);

        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("Sofia"))
                .andExpect(jsonPath("$[0].password").value("Conejitos"))
                .andExpect(jsonPath("$[1].username").value("Isabel"))
                .andExpect(jsonPath("$[1].password").value("Perritos"));
    }

    @Test
    public void GetUserById() throws Exception{
        when(userService.getUserById(2)).thenReturn(Optional.of(user2));

        mockMvc.perform(get("/api/user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    public void UpdateUser() throws Exception{
        when(userService.updateUser(user1, 1)).thenReturn(user1);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user1);

        mockMvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void DeleteUser() throws Exception{
        when(userService.deleteUser(1)).thenReturn(true);

        mockMvc.perform(delete("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User with id 1 was deleted."));
    }

}

