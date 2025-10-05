package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.business.UserService;
import com.duocvinavalpo.LuxSentinel.model.User;
import com.duocvinavalpo.LuxSentinel.model.repository.UserRepository;
import com.duocvinavalpo.LuxSentinel.security.JwtService;
import com.duocvinavalpo.LuxSentinel.web.AuthRequest;
import com.duocvinavalpo.LuxSentinel.web.AuthResponse;
import com.duocvinavalpo.LuxSentinel.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        //TODO: This is a simplified, hence, insecure implementation. Change BCrypt encoder in order to improve security
        user.setPassword(passwordEncoder.encode(userDTO.getUsername()));
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");
        userRepository.save(user);
        return userDTO;
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user.getUsername(), Map.of("role", user.getRole()));

        AuthResponse response = new AuthResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setToken(token);
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
