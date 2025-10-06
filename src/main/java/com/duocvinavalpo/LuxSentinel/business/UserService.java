package com.duocvinavalpo.LuxSentinel.business;

import com.duocvinavalpo.LuxSentinel.entity.User;
import com.duocvinavalpo.LuxSentinel.web.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    UserDTO createUser(UserDTO userDto);
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(UUID id);
}
