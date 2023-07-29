package com.win.server.controller;

import com.win.server.DTO.auth.SimpleMessage;
import com.win.server.DTO.auth.TokenResponse;
import com.win.server.security.ContextUserManager;
import com.win.server.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtProvider jwtProvider;

    @GetMapping("/token/refresh")
    @ResponseStatus(HttpStatus.OK)
    public SimpleMessage getAccessToken() {
        String token = jwtProvider.generateToken(ContextUserManager.getUserId(), 120000L);
        return new SimpleMessage("accessToken", 200, token);
    }
}
