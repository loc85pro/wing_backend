package com.win.server.DTO.auth.basic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
public class DefaultRegisterModel {
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Full name is required")
    private String full_name;
    @NotEmpty(message = "Password is required")
    private String password;
}
