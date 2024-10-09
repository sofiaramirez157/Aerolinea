
package com.example.Aerolinea.user;

import com.example.Aerolinea.controller.UserController;
import com.example.Aerolinea.dto.UserDTO;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.service.UserService;
import com.example.Aerolinea.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");

        userList = Collections.singletonList(user);
    }

    @Test
    void createUserTest() {
        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getUsername(), response.getBody().getUsername());
    }

    @Test
    void getAllUsersTest() {
        when(userService.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(user.getId(), response.getBody().get(0).getId());
        assertEquals(user.getUsername(), response.getBody().get(0).getUsername());
    }

    @Test
    void getUserByIdTest() {
        when(userService.getUserById(1L)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getUsername(), response.getBody().getUsername());
    }

    @Test
    void updateUserTest() {
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getRole());
        when(userService.updateUser(any(User.class), any(Long.class))).thenReturn(user);

        ResponseEntity<Void> response = userController.updateUser(userDTO, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).updateUser(any(User.class), any(Long.class));
    }

    @Test
    void deleteUserByIdTest() {
        when(userService.deleteUser(1L)).thenReturn(true);

        ResponseEntity<String> response = userController.deleteUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User with ID 1 was deleted.", response.getBody());
        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteUserNotFoundTest() {
        when(userService.deleteUser(1L)).thenThrow(new UserNotFoundException("User with ID 1 not found."));

        ResponseEntity<String> response = userController.deleteUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 1 not found.", response.getBody());
    }
}