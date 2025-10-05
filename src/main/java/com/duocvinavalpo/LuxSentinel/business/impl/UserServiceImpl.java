package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.business.UserService;
import com.duocvinavalpo.LuxSentinel.model.User;
import com.duocvinavalpo.LuxSentinel.model.repository.UserRepository;
import com.duocvinavalpo.LuxSentinel.web.AuthRequest;
import com.duocvinavalpo.LuxSentinel.web.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
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

    public AuthResponse authenticate(AuthRequest request){
        //TODO: Impl logic for authentication
        return new AuthResponse();
    }
}
