package com.duocvinavalpo.LuxSentinel.web.authservice;

import com.duocvinavalpo.LuxSentinel.web.AuthRequest;
import com.duocvinavalpo.LuxSentinel.web.AuthResponse;
import com.duocvinavalpo.LuxSentinel.web.dto.UserDTO;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
    UserDTO register(UserDTO userDTO);
}
