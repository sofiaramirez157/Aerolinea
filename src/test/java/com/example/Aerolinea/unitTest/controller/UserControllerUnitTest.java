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
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        User response = userController.createUser(user);

        assertEquals(user.getId(), response.getId());
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    void testGetAllReservations() {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        List<User> response = userController.getAllUsers();

        assertEquals(user.getId(), response.get(0).getId());
        assertEquals(user.getUsername(), response.get(0).getUsername());
        assertEquals(user.getPassword(),response.get(0).getPassword());
        assertEquals(user.getRole(), response.get(0).getRole());
    }

    @Test
    void testGetReservationById() {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> response = userController.getUserById(1l);

        assertTrue(response.isPresent());
        assertEquals(user.getId(), response.get().getId());
        assertEquals(user.getUsername(), response.get().getUsername());
        assertEquals(user.getPassword(), response.get().getPassword());
        assertEquals(user.getRole(), response.get().getRole());
    }

    @Test
    void testUpdateReservation() {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("New Paris");
        updatedUser.setPassword("France");
        updatedUser.setRole(ERole.ROLE_USER);

        when(userService.updateUser(updatedUser, 1L)).thenReturn(updatedUser);

        User response = userController.updateUser(updatedUser, 1L);

        assertEquals(updatedUser.getId(), response.getId());
        assertEquals(updatedUser.getUsername(), response.getUsername());
        assertEquals(updatedUser.getPassword(), response.getPassword());
        assertEquals(updatedUser.getRole(), response.getRole());
    }

    @Test
    void testDeleteReservation() {
        when(userService.deleteUser(1L)).thenReturn(true);

        String response = userController.deleteUserById(1L);

        assertEquals("User with id 1 was deleted.", response);
    }
}

