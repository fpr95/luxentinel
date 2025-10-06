package com.duocvinavalpo.LuxSentinel.web.authservice.impl;

import com.duocvinavalpo.LuxSentinel.entity.User;
import com.duocvinavalpo.LuxSentinel.entity.repository.UserRepository;
import com.duocvinavalpo.LuxSentinel.security.JwtService;
import com.duocvinavalpo.LuxSentinel.web.AuthRequest;
import com.duocvinavalpo.LuxSentinel.web.AuthResponse;
import com.duocvinavalpo.LuxSentinel.web.authservice.AuthService;
import com.duocvinavalpo.LuxSentinel.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager; // OK aquí
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        // Si falla lanzará AuthenticationException
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        String token = jwtService.generateToken(user.getUsername(), Map.of("role", user.getRole()));

        AuthResponse resp = new AuthResponse();
        resp.setToken(token);
        resp.setUsername(user.getUsername());
        resp.setRole(user.getRole());
        return resp;
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword() == null ? userDTO.getUsername() : userDTO.getPassword()));
        user.setRole(userDTO.getRole() == null ? "USER" : userDTO.getRole());
        userRepository.save(user);

        UserDTO out = new UserDTO();
        out.setId(user.getId());
        out.setUsername(user.getUsername());
        out.setEmail(user.getEmail());
        out.setRole(user.getRole());
        return out;
    }
}
