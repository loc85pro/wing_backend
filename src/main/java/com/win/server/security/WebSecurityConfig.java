package com.win.server.security;

import com.win.server.service.AuthService;
import jakarta.servlet.FilterChain;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig  {
    private JwtProvider jwtProvider;
    private CustomUserDetailService customUserDetailService;
    private AuthService authService;
    @Bean
    public SecurityFilterChain configFilterChain(HttpSecurity http) throws Exception{
        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider,customUserDetailService,authService), BasicAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return  (web) -> web.ignoring().requestMatchers("/login/*","/register/*");
    }

}
