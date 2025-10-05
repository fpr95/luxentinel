package com.duocvinavalpo.LuxSentinel.web;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String username;
    private String role;
}
