package com.duocvinavalpo.LuxSentinel.web.controller;

import com.duocvinavalpo.LuxSentinel.business.UserService;
import com.duocvinavalpo.LuxSentinel.model.User;
import com.duocvinavalpo.LuxSentinel.web.AuthRequest;
import com.duocvinavalpo.LuxSentinel.web.AuthResponse;
import com.duocvinavalpo.LuxSentinel.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        // Aquí deberías integrar JWT o Spring Security
        return userService.authenticate(request);
    }

    //TODO: The return type could be as well boolean. Check business logic
    @PostMapping("/register")
    public User register(@RequestBody UserDTO userDTO) {
        User user = new User();
        return userService.createUser(user);
    }
}
