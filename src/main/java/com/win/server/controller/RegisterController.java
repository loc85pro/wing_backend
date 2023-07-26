package com.win.server.controller;

import com.win.server.entity.UserEntity;
import com.win.server.DTO.auth.basic.DefaultRegisterModel;
import com.win.server.DTO.auth.TokenResponse;
import com.win.server.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.BindException;
import java.util.UUID;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
@Tag(name="Auth")
public class RegisterController {
    private AuthService authService;

    @PostMapping("/basic")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Request data is malformed")})
    public TokenResponse registerByDefault(@RequestBody @Valid DefaultRegisterModel data)  {
        UserEntity newUser = new UserEntity();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUser_name(UUID.randomUUID().toString());
        newUser.setFull_name(data.getFull_name());
        newUser.setPassword(data.getPassword());
        newUser.setEmail(data.getEmail());
        return authService.registerUser(newUser);
    }
}
