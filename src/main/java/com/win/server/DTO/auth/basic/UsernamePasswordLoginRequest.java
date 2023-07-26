package com.win.server.DTO.auth.basic;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernamePasswordLoginRequest {
    @NotEmpty(message = "Username must not be empty")
    private String username;
    @NotEmpty(message = "Password must not be empty")
    private String password;
}
