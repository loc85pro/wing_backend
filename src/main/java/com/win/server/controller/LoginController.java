package com.win.server.controller;

import com.win.server.DTO.ErrorResponse;
import com.win.server.DTO.auth.basic.EmailPasswordLoginRequest;
import com.win.server.DTO.auth.TokenResponse;
import com.win.server.DTO.auth.basic.UsernamePasswordLoginRequest;
import com.win.server.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@Tag(name="Login")
public class LoginController {
    private AuthService authService;
    @PostMapping("/basic/email-password")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse emailPasswordLogin(@RequestBody @Valid EmailPasswordLoginRequest request) {
             return authService.emailPasswordLogin(request.getEmail(), request.getPassword());
    }

    @PostMapping("/basic/username-password")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse usernamePassword(@RequestBody @Valid UsernamePasswordLoginRequest request) {
        return authService.usernamePasswordLogin(request.getUsername(), request.getPassword());
    }


    //--------------------- OAUTH ------------------
    @GetMapping("/oauth2/google/authorization")
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse getAuthorizationURLGoogle() {

        return new ErrorResponse("https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fgoogle&prompt=consent&response_type=code&client_id=286076435907-6nfi8v3sd5gchj8rp091gncul553um6f.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&access_type=offline",200);
    }

    @GetMapping("/oauth2/google")
    @ResponseStatus(HttpStatus.OK)
    public void getCodeGoogle(@RequestParam String code, HttpServletResponse response) throws IOException {
        TokenResponse token = authService.oauth2GoogleLogin(code);
        response.sendRedirect("http://localhost:5173/catch_token?accessToken="+token.getAccessToken()+"&refreshToken="+token.getRefreshToken());
    }
}