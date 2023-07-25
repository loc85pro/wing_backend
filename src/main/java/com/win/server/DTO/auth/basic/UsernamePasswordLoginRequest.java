package com.win.server.DTO.auth.basic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernamePasswordLoginRequest {
    private String username;
    private String password;
}
