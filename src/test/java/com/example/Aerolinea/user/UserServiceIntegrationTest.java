package com.example.Aerolinea.user;

import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.repository.IUserRepository;
import com.example.Aerolinea.service.UserService;
import com.example.Aerolinea.exceptions.UserNotFoundException;
import com.example.Aerolinea.exceptions.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceIntegrationTest {

    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("Sofia");
        user1.setPassword("perritos");
        user1.setRole(ERole.ROLE_USER);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("Isabel");
        user2.setPassword("conejitos");
        user2.setRole(ERole.ROLE_USER);
    }

    @Test
    void createUser() {
        when(iUserRepository.save(any(User.class))).thenReturn(user2);

        User newUser = userService.createUser(user2);

        assertNotNull(newUser);
        assertEquals(2L, newUser.getId());
        assertEquals("Isabel", newUser.getUsername());
        assertEquals("conejitos", newUser.getPassword());
        assertEquals(ERole.ROLE_USER, newUser.getRole());

        verify(iUserRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(iUserRepository.findAll()).thenReturn(userList);

        List<User> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));

        verify(iUserRepository, times(1)).findAll();
    }

    @Test
    void getUserByIdTest() {
        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user1));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("Sofia", foundUser.getUsername());
    }

    @Test
    void updateUser() {
        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user1));

        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("Updated Sofia");
        userToUpdate.setPassword("perritos");
        userToUpdate.setRole(ERole.ROLE_USER);

        when(iUserRepository.save(any(User.class))).thenReturn(userToUpdate);

        User actualUpdatedUser = userService.updateUser(userToUpdate, 1L);

        assertNotNull(actualUpdatedUser);
        assertEquals("Updated Sofia", actualUpdatedUser.getUsername());
        assertEquals(1L, actualUpdatedUser.getId());

        verify(iUserRepository).findById(1L);
        verify(iUserRepository).save(any(User.class));
    }

    @Test
    void updateUserNotFound() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("Updated Sofia");
        userToUpdate.setPassword("newPassword");

        when(iUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userToUpdate, 1L));
    }

    @Test
    void deleteUser() {
        long id = 2L;

        when(iUserRepository.existsById(id)).thenReturn(true);

        boolean result = userService.deleteUser(id);

        assertTrue(result);
        verify(iUserRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteUserNotFound() {
        long id = 2L;

        when(iUserRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
    }

    @Test
    void validateUserTest() {
        User invalidUser = new User();
        assertThrows(InvalidRequestException.class, () -> userService.createUser(invalidUser));

        invalidUser.setUsername("TestUser");
        invalidUser.setPassword(null);

        assertThrows(InvalidRequestException.class, () -> userService.createUser(invalidUser));
    }
}