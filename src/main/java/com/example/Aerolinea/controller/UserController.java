package com.example.Aerolinea.controller;

import com.example.Aerolinea.exceptions.ResourceNotFoundException;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        return userService.updateUser(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable long id) {
        boolean ok = userService.deleteUser(id);
        if (ok) {
            return "User with id " + id + " was deleted.";
        } else {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
    }
}