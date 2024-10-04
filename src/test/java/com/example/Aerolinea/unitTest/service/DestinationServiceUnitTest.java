package com.example.Aerolinea.unitTest.service;

import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.repositories.IUserRepository;
import com.example.Aerolinea.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
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
        user.setRole(ERole.ROLE_USER);

    }

            @Test
            void createUserTest () {
            when(iUserRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

            User createdUser = userService.createUser(user);

            assertNotNull(createdUser);
            assertEquals(user.getId(), createdUser.getId());
            assertEquals(user.getUsername(), createdUser.getUsername());
            assertEquals(user.getPassword(), createdUser.getPassword());
            assertEquals(user.getRole(), createdUser.getRole());

        }

            @Test
            void getAllUserTest () {
            List<User> userList = new ArrayList<>();
            userList.add(user);

            when(iUserRepository.findAll()).thenReturn(userList);

            List<User> users = userService.getAllUsers();

            assertNotNull(user);
            assertEquals(1, user.size);
            assertEquals(user.getId(), user.get(0).getId());
        }

            @Test
            void getUserByIdTest () {
            when(iUserRepository.findById(1L)).thenReturn(Optional.of(user));

            Optional<User> foundUser = userService
                    .getUserById(user.getId());

            assertTrue(foundUser.isPresent());
            assertEquals(user.getId(), foundUser.get().getId());
        }


            @Test
            void deleteUserTest () {
            when(iUserRepository.existsById(user.getId())).thenReturn(true);

            boolean isDelete = userService.deleteUser(user.getId());

            assertTrue(isDelete);
            verify(iUserRepository, times(1)).deleteById(user.getId());
    }
}

