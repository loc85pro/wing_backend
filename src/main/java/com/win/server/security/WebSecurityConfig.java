package com.win.server.security;

import com.win.server.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig  {
    private JwtProvider jwtProvider;
    private CustomUserDetailService customUserDetailService;
    private AuthService authService;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return  (web) -> web.ignoring().requestMatchers("/login/*","/register/*");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtProvider,customUserDetailService,authService), BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public WebMvcConfigurer corsConfigugrer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST").allowedHeaders("*");
            }
        };
    }
}
