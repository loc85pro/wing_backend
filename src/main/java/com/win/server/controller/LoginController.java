package com.win.server.controller;

import com.win.server.model.*;
import com.win.server.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    private AuthService authService;
    @PostMapping("/basic")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenResponse basicLogin(@RequestBody LoginRequestModel data) {
             return authService.usernamePasswordLogin(data.getUsername(), data.getPassword());
    }

    @GetMapping("/oauth2/google/authorization")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ErrorResponse getAuthorizationURLGoogle() {

        return new ErrorResponse("https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fgoogle&prompt=consent&response_type=code&client_id=286076435907-6nfi8v3sd5gchj8rp091gncul553um6f.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&access_type=offline",200);
    }

    @GetMapping("/oauth2/google")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getCodeGoogle(@RequestParam String code, HttpServletResponse response) throws IOException {
        TokenResponse token = authService.oauth2GoogleLogin(code);
        response.sendRedirect("http://localhost:5173/catch_token?accessToken="+token.getAccessToken()+"?refreshToken="+token.getRefreshToken());
    }
}