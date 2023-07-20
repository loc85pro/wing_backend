package com.win.server.controller;

import com.win.server.DTO.UserDTO;
import com.win.server.security.ContextUserManager;
import com.win.server.service.UserService;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private ContextUserManager contextUserManager;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO getOwnData() {
        return userService.getUserById(contextUserManager.getUsername());
    }
}
