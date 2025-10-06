package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.business.UserService;
import com.duocvinavalpo.LuxSentinel.entity.User;
import com.duocvinavalpo.LuxSentinel.entity.repository.UserRepository;
import com.duocvinavalpo.LuxSentinel.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final JwtService jwtService;
    //private final AuthenticationManager authenticationManager;

    /*
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
     */
    @Override
    public User createUser(User user) {
        // Validar si ya existe el username
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword())); // 🔒 usar password, no username
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");

        User saved = createUser(user); // delegamos la lógica
        UserDTO dto = new UserDTO();
        dto.setEmail(saved.getEmail());
        dto.setRole(saved.getRole());
        dto.setUsername(saved.getUsername());
        return dto;
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
    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
