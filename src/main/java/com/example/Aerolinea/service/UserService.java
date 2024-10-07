package com.example.Aerolinea.service;

import com.example.Aerolinea.model.User;
import com.example.Aerolinea.repositories.IUserRepository;
import com.example.Aerolinea.exceptions.UserNotFoundException;
import com.example.Aerolinea.exceptions.InvalidRequestException;
import com.example.Aerolinea.exceptions.UnauthorizedAccessException;
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

    public void updateUser(User user, long id) {
        getUserById(id);
        validateUser(user);
        user.setId(id);
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public void checkAccess(long userId, long currentUserId) {
        if (userId != currentUserId) {
            throw new UnauthorizedAccessException("You are not authorized to access this user's data.");
        }
    }

    private void validateUser(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            throw new InvalidRequestException("Invalid user data provided.");
        }
    }
}