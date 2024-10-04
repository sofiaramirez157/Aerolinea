package com.example.Aerolinea.unitTest.controller;

import com.example.Aerolinea.controller.UserController;
import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UserControllerUnitTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("Sofia");
        user.setPassword("Conejitos");
        user.setRole(ERole.ROLE_USER);
    }

    @Test
    void testCreateReservation() {
        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user.getId(), response.getBody().getId());
    }

    @Test
    void testGetAllReservations() {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, ((List<?>) Objects.requireNonNull(response.getBody())).size());
    }

    @Test
    void testGetReservationById() {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateReservation() {
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(user, 1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user.getId(), response.getBody().getId());
    }

    @Test
    void testDeleteReservation() {
        ResponseEntity<String> response = userController.deleteUserById(1L);

        assertEquals(204, response.getStatusCodeValue());
    }
}

