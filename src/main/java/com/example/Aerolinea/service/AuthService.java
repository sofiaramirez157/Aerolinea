package com.example.Aerolinea.service;

import com.example.Aerolinea.dto.request.LoginRequest;
import com.example.Aerolinea.dto.request.RegisterRequest;
import com.example.Aerolinea.dto.response.AuthResponse;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtService.generateToken(userDetails);

        if (userDetails instanceof User user) {
            return AuthResponse.builder()
                    .token(token)
                    .role(user.getRole())
                    .build();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());

        userRepository.save(user);

        String token = jwtService.generateToken(userDetailsService.loadUserByUsername(registerRequest.getUsername()));

        return AuthResponse.builder()
                .token(token)
                .role(user.getRole())
                .build();
    }
}