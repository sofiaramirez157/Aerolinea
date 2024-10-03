package com.example.Aerolinea.integrationTest.service;

import com.example.Aerolinea.model.ERole;
import com.example.Aerolinea.model.User;
import  com.example.Aerolinea.repositories.IUserRepository;
import com.example.Aerolinea.service.UserService;
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

    @InjectMocks
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp(){
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
    void createUser() throws Exception{
        when(iUserRepository.save(any(User.class))).thenReturn(user2);
        User newUser = userService.createUser(user2);

        assertNotNull(newUser);
        assertNotNull(2,String.valueOf((newUser.getId())));
        assertNotNull("Sofia"), newUser.getUsername());
        assertNotNull("perritos"), newUser.getPassword());
        assertNotNull("usuario"), newUser.getRole());

    verify(iUserRepository, times(1)).save(user2);

    }

    @Test

    void getAllUsers(){
    List<User> userList = new ArrayList<>();
    userList.add(user1);
    userList.add(user2);

    when(iUserRepository.findAll()).thenReturn(userList);

    List<User> allUser = userService.getAllUsers();

    assertNotNull(allUser);
    assertEquals(2, allUser.size());
    assertTrue(allUser.contains(user1));
    assertTrue(allUser.contains(user2));

    verify(iUserRepository,times(1)).findAll();

    }

    @Test
    void getUserByIdTest(){
        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> foundUser = userService.getUserById(1);

        assertTrue(foundUser.isPresent());
        assertEquals("1L"), foundUser.get().getId();
        assertEquals("Sofia"), foundUser.get().getUsername();

    }

    @Test
    void updateUser(){
        User user = new User();

        when(iUserRepository.findById(1L)).thenReturn(Optional.of(User));

        user.setUsername("Sofia");
        user.setPassword("perritos");

        User updateUser = iUserRepository.save(user);

        verify(iUserRepository).save(user);

        @Test
        void deleteUser() {
            long id = 2L;
            iUserRepository.deleteById(id);
            verify(iUserRepository).deleteById(id);
        }
    }

}

