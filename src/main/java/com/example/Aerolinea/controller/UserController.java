package com.example.Aerolinea.controller;

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
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody User user, @PathVariable long id) {
        userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
        return "User with ID " + id + " was deleted.";
    }
}