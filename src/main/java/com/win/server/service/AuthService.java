package com.win.server.service;

import com.win.server.exception.myexception.IncorrectPasswordException;
import com.win.server.exception.myexception.UserNotFoundException;
import com.win.server.model.TokenResponse;
import com.win.server.repository.UserRepository;
import com.win.server.security.AdminAuthority;
import com.win.server.security.CustomUserDetailService;
import com.win.server.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;



@Service
@AllArgsConstructor
public class AuthService {
    UserRepository userRepository;
    JwtProvider jwtProvider;
    CustomUserDetailService userDetailService;

//    public AuthService(UserRepository userRepository, JwtProvider jwtProvider, CustomUserDetailService userDetailService) {
//        this.userRepository = userRepository;
//        this.jwtProvider = jwtProvider;
//        this.userDetailService = userDetailService;
//    }

    public TokenResponse usernamePasswordLogin(String username, String password) {
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if (userDetails == null)
            throw new UserNotFoundException(username);
        if (!userDetails.getPassword().equals(password))
            throw new IncorrectPasswordException();
        setUserContext(userDetails);
        String accessToken = jwtProvider.generateToken(username, 120000L); //2 minutes
        String refreshToken = jwtProvider.generateToken(username, 1800000L); //2 minutes
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse registerUser() {}

    //----------------------------------------------------

    public void setUserContext(UserDetails userDetails) {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new AdminAuthority());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), roles);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
    }

    //-------------OAUTH2 GOOGLE--------------
    public TokenResponse oauth2GoogleLogin() {
        String scope = "https://www.googleapis.com/auth/userinfo.profile";
        String client_id = "286076435907-6nfi8v3sd5gchj8rp091gncul553um6f.apps.googleusercontent.com";
        String redirect = "http://localhost:8080/login/oauth2/google";
        String response_type = "code";
        return null;
    }
}
