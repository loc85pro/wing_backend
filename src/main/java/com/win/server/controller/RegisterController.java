package com.win.server.controller;

import com.win.server.entity.UserEntity;
import com.win.server.DTO.DefaultRegisterModel;
import com.win.server.DTO.TokenResponse;
import com.win.server.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private AuthService authService;

    @PostMapping("/basic")
    public TokenResponse registerByDefault(@RequestBody DefaultRegisterModel data) {
        UserEntity newUser = new UserEntity();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUser_name(UUID.randomUUID().toString());
        newUser.setFull_name(data.getFull_name());
        newUser.setPassword(data.getPassword());
        newUser.setEmail(data.getEmail());
        return authService.registerUser(newUser);
    }
}
