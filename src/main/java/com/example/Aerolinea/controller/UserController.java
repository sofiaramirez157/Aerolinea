package com.example.Aerolinea.controller;

import com.example.Aerolinea.model.User;
import com.example.Aerolinea.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin

public class UserController {
    UserService userService;

    @PostMapping(path = "/")
    public User createUser(@RequestBody User user){
        return userService.createService(user);
    }

    @GetMapping(path = "/")
    public List<User> getAllUser(){
        return userService.getAlluser();
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getUserId(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PutMapping(path = "/{id}")
    public void updateUser(@RequestBody User user, @PathVariable long id){
        userService.updateUser(User, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteUserById(@PathVariable long id){
        boolean ok = userService.deleteUser(id);
        if (ok){
            return "User with id" + id + "was delete";
        } else {
            return "User with id" + id + "not found";
        }
    }
}

