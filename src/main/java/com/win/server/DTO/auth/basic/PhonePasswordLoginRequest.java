package com.win.server.DTO.auth.basic;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PhonePasswordLoginRequest {
    @NotEmpty(message = "Phone must not be empty")
    private String phone;
    @NotEmpty(message = "Password must not be empty")
    private String password;
}
