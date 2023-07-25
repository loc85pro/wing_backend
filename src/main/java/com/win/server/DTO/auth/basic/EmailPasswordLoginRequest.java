package com.win.server.DTO.auth.basic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class EmailPasswordLoginRequest {
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
}
