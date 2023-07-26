package com.win.server.controller;

import com.win.server.DTO.UserDTO;
import com.win.server.security.ContextUserManager;
import com.win.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "Personal Data")
public class UserController {
    private UserService userService;
    private ContextUserManager contextUserManager;

    @Operation(description = "Get own data (Brief data)")
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getOwnData() {
        return userService.getUserById(contextUserManager.getUsername());
    }
}
