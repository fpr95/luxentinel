package com.duocvinavalpo.LuxSentinel.business;

import com.duocvinavalpo.LuxSentinel.model.User;
import com.duocvinavalpo.LuxSentinel.web.AuthRequest;
import com.duocvinavalpo.LuxSentinel.web.AuthResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    AuthResponse authenticate(AuthRequest request);
    User createUser(User user);
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(UUID id);
}
