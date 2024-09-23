package com.example.Aerolinea.service;

import com.example.Aerolinea.model.User;
import com.example.Aerolinea.repositories.IUserRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private IUserRepository iUserRepository;

    public User createUser(User user) {
        return iUserRepository.save(user);
    }

    public List<User> getAllUser() {
        try {
            return iUserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user.", e);
        }
    }

    public Optional<User> getUserById(long id) {
        try {
            return i.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user details.", e);
        }
    }

    public void updateUser(User user, long id) {
        User.setId(id);
        iUserRepository.save(user);
    }

    public boolean deleteUser(long id) {
        try {
            iUserRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
