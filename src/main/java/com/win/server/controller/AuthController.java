package com.win.server.controller;

import com.win.server.DTO.auth.SimpleMessage;
import com.win.server.security.ContextUserManager;
import com.win.server.security.JwtProvider;
import com.win.server.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtProvider jwtProvider;
    private final MailService mailService;

    @GetMapping("/token/refresh")
    @ResponseStatus(HttpStatus.OK)
    public SimpleMessage getAccessToken() {
        String token = jwtProvider.generateToken(ContextUserManager.getUserId(), 120000L);
        return new SimpleMessage("accessToken", 200, token);
    }

}
