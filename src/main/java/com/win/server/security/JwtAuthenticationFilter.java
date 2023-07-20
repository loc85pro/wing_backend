package com.win.server.security;


import com.win.server.exception.myexception.TokenNotFoundException;
import com.win.server.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private JwtProvider jwtProvider;
    private CustomUserDetailService userDetailService;
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("This is JWT");
        String token = getTokenFromRequest(request);
        String userId = jwtProvider.getUserIdFromToken(token);
        UserDetails userDetail = userDetailService.loadUserByUsername(userId);
        authService.setUserContext(userDetail);
        filterChain.doFilter(request,response);
    }
    private String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println("Token from request: "+token);
        if (token != null && token.startsWith("Bearer "))
            return token.replace("Bearer ", "");

        throw new TokenNotFoundException("");
    }
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println("not filter JWT");
        String requestPath = request.getServletPath();
        System.out.println("Hello: " + requestPath);
        String[] whiteList = {"/login/basic","/register/basic",};
        for (String path : whiteList) {
            if (path.equals(requestPath))
                return true;
        }
        return false;
    }
}
