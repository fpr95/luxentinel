package com.duocvinavalpo.LuxSentinel.web.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String role;
    private String password;
}
