package com.example.Aerolinea.service;

import com.example.Aerolinea.model.User;
import com.example.Aerolinea.repository.IUserRepository;
import com.example.Aerolinea.exceptions.UserNotFoundException;
import com.example.Aerolinea.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public User updateUser(User user, long id) {
        validateUser(user);
        User existingUser = getUserById(id);

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    public boolean deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        return true;
    }

    private void validateUser(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            throw new InvalidRequestException("Invalid user data provided.");
        }
    }
}