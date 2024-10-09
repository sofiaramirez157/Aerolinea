
package com.example.Aerolinea.user;

import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.repository.IUserRepository;
import com.example.Aerolinea.service.UserService;
import com.example.Aerolinea.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {
    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("Sofia");
        user.setPassword("perritos");
        user.setRole(ERole.USER);
    }

    @Test
    void createUserTest() {
        when(iUserRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getRole(), createdUser.getRole());
    }

    @Test
    void getAllUsersTest() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(iUserRepository.findAll()).thenReturn(userList);

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
        assertEquals(user.getUsername(), users.get(0).getUsername());
    }

    @Test
    void getUserByIdTest() {
        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void getUserByIdNotFoundTest() {
        when(iUserRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(2L));
    }

    @Test
    void deleteUserTest() {
        when(iUserRepository.existsById(user.getId())).thenReturn(true);

        boolean isDeleted = userService.deleteUser(user.getId());

        assertTrue(isDeleted);
        verify(iUserRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUserNotFoundTest() {
        when(iUserRepository.existsById(user.getId())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user.getId()));
    }
}